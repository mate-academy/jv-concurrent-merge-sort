package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
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
        if (end - start <= THRESHOLD) {
            Arrays.sort(array, start, end);
        } else {
            int middle = start + (end - start) / 2;
            MergeSortAction left = new MergeSortAction(array, start, middle);
            MergeSortAction right = new MergeSortAction(array, middle, end);

            invokeAll(left, right);
            merge(middle);
        }
    }

    private void merge(int middle) {
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
