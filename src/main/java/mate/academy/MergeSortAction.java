package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;

    private final int[] array;

    private final int start;

    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start + 1 <= THRESHOLD) {
            Arrays.sort(array, start, end + 1);
        } else {
            int middle = start + (end - start) / 2;

            MergeSortAction left = new MergeSortAction(array, start, middle);
            MergeSortAction right = new MergeSortAction(array, middle + 1, end);

            invokeAll(left, right);
            merge(array, start, middle, end);
        }
    }

    private void merge(int[] array, int start, int middle, int end) {
        int[] left = Arrays.copyOfRange(array, start, middle + 1);
        int[] right = Arrays.copyOfRange(array, middle + 1, end + 1);
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
