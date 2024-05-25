package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;

    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
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
            int middle = (start + end) / 2;
            MergeSortAction left = new MergeSortAction(array, start, middle);
            MergeSortAction right = new MergeSortAction(array, middle, end);
            invokeAll(left, right);

            mergeArrays(start, middle, end);
        }
    }

    private void mergeArrays(int start, int middle, int end) {
        int[] mergedArray = new int[end - start];
        int left = start;
        int right = middle;
        int mergeIndex = 0;

        while (left < middle && right < end) {
            if (array[left] <= array[right]) {
                mergedArray[mergeIndex++] = array[left++];
            } else {
                mergedArray[mergeIndex++] = array[right++];
            }
        }

        while (left < middle) {
            mergedArray[mergeIndex++] = array[left++];
        }

        while (right < end) {
            mergedArray[mergeIndex++] = array[right++];
        }

        System.arraycopy(mergedArray, 0, array, start, mergedArray.length);
    }
}
