package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    @Override
    protected void compute() {
        if (end - start <= 1) {
            return;
        } else {
            int middle = start + (end - start) / 2;

            MergeSortAction leftTask = new MergeSortAction(array, start, middle);
            MergeSortAction rightTask = new MergeSortAction(array, middle, end);

            invokeAll(leftTask, rightTask);

            merge(array, start, middle, end);
        }
    }

    private void merge(int[] array, int start, int middle, int end) {
        int[] left = Arrays.copyOfRange(array, start, middle);
        int[] right = Arrays.copyOfRange(array, middle, end);

        int i = 0;
        int j = 0;
        int k = start;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
