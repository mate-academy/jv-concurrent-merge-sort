package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 1;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length > THRESHOLD) {
            int middle = array.length / 2;
            int[] left = Arrays.copyOfRange(array, 0, middle);
            int[] right = Arrays.copyOfRange(array, middle, array.length);
            MergeSortAction leftAction = new MergeSortAction(left);
            MergeSortAction rightAction = new MergeSortAction(right);
            invokeAll(leftAction, rightAction);
            merge(array, left, right);
        }
    }

    private void merge(int[] array, int[] left, int[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                array[resultIndex++] = left[leftIndex++];
            } else {
                array[resultIndex++] = right[rightIndex++];
            }
        }

        while (leftIndex < left.length) {
            array[resultIndex++] = left[leftIndex++];
        }

        while (rightIndex < right.length) {
            array[resultIndex++] = right[rightIndex++];
        }
    }
}
