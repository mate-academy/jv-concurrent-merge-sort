package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    public static final int THRESHOLD = 2;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.start = left;
        this.end = right;
    }

    @Override
    protected void compute() {
        int middle = (start + end) / 2;
        if (end - start >= THRESHOLD) {
            invokeAll(
                    new MergeSortAction(array, start, middle),
                    new MergeSortAction(array, middle, end)
            );
        } else {
            Arrays.sort(array);
        }
    }

    private void mergeAndSort(int start, int end, int middle) {
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
