package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this.array = array;
        start = 0;
        end = array.length;
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= 4) {
            Arrays.sort(array, start, end);
            return;
        }

        int middle = (start + end) / 2;

        MergeSortAction left = new MergeSortAction(array, start, middle);
        MergeSortAction right = new MergeSortAction(array, middle, end);

        invokeAll(left, right);

        merge();
    }

    private void merge() {
        int[] temp = Arrays.copyOfRange(array, start, end);
        int middle = temp.length / 2;
        int left = 0;
        int right = middle;
        int tempIndex = start;

        while (left < middle && right < end - start) {
            if (temp[left] < temp[right]) {
                array[tempIndex] = temp[left];
                left++;
            } else {
                array[tempIndex] = temp[right];
                right++;
            }
            tempIndex++;
        }

        while (left < middle) {
            array[tempIndex] = temp[left];
            left++;
            tempIndex++;
        }

        while (right < end - start) {
            array[tempIndex] = temp[right];
            right++;
            tempIndex++;
        }
    }
}
