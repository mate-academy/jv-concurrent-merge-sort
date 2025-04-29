package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[]array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            sortArray(array, start, end);
        } else {
            int middle = start + (end - start) / 2;
            MergeSortAction leftAction = new MergeSortAction(array, start, middle);
            MergeSortAction rightAction = new MergeSortAction(array, middle, end);

            invokeAll(leftAction, rightAction);
            merge(array, start, middle, end);
        }
    }

    private void sortArray(int[] array, int start, int end) {
        for (int i = start; i < end - 1; i++) {
            for (int j = start; j < end - 1 - (i - start); j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    private void merge(int[] array, int start, int middle, int end) {
        int[] left = Arrays.copyOfRange(array, start, middle);
        int[] right = Arrays.copyOfRange(array, middle, end);
        int i = 0;
        int j = 0;
        int k = start;

        while (i < left.length && j < right.length) {
            array[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
