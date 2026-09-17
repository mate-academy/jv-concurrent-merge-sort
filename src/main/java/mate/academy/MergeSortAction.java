package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this.array = array;
        this.start = 0;
        this.end = array.length;
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= 1) {
            return;
        }

        int mid = (start + end) / 2;

        MergeSortAction left =
                new MergeSortAction(array, start, mid);

        MergeSortAction right =
                new MergeSortAction(array, mid, end);

        left.fork();
        right.fork();

        left.join();
        right.join();

        merge(mid);
    }

    private void merge(int mid) {

        int[] temp = new int[end - start];

        int left = start;
        int right = mid;
        int index = 0;

        while (left < mid && right < end) {

            if (array[left] <= array[right]) {
                temp[index] = array[left];
                left++;
            } else {
                temp[index] = array[right];
                right++;
            }

            index++;
        }

        while (left < mid) {
            temp[index] = array[left];
            left++;
            index++;
        }

        while (right < end) {
            temp[index] = array[right];
            right++;
            index++;
        }

        for (int i = 0; i < temp.length; i++) {
            array[start + i] = temp[i];
        }
    }
}