package mate.academy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private int[] array;
    private int leftIndex;
    private int rightIndex;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int leftIndex, int rightIndex) {
        this.array = array;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    @Override
    protected void compute() {
        int distance = rightIndex - leftIndex;

        if (distance < 2) {
            return;
        }
        if (distance == 2) {
            if (array[leftIndex] > array[leftIndex + 1]) {
                int temp = array[leftIndex];
                array[leftIndex] = array[leftIndex + 1];
                array[leftIndex + 1] = temp;
            }
        }
        if (distance > 2) {
            int center = leftIndex + (distance) / 2;
            MergeSortAction left = new MergeSortAction(array, leftIndex, center);
            MergeSortAction right = new MergeSortAction(array, center, rightIndex);

            left.fork();
            right.compute();
            left.join();

            List<Integer> list = new ArrayList<>();
            int l = leftIndex;
            int r = center;
            while (l < center && r < rightIndex) {
                if (array[l] > array[r]) {
                    list.add(array[r]);
                    r++;
                } else {
                    list.add(array[l]);
                    l++;
                }
            }
            while (l < center) {
                list.add(array[l]);
                l++;
            }
            while (r < rightIndex) {
                list.add(array[r]);
                r++;
            }

            for (int i = 0; i < list.size(); i++) {
                array[i + leftIndex] = list.get(i);
            }
        }
    }
}
