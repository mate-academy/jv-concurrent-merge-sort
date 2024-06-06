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
            int[] leftArray = Arrays.copyOfRange(array, 0, middle);
            int[] rightArray = Arrays.copyOfRange(array, middle, array.length);
            MergeSortAction leftSortAction = new MergeSortAction(leftArray);
            MergeSortAction rightSortAction = new MergeSortAction(rightArray);
            invokeAll(leftSortAction, rightSortAction);
            mergeArrays(leftArray, rightArray);
        }
    }

    private void mergeArrays(int[] left, int[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int arrayIndex = 0;

        int leftLength = left.length;
        int rightLength = right.length;

        while (leftIndex < leftLength || rightIndex < rightLength) {
            if (leftIndex < leftLength
                    && (rightIndex >= rightLength || left[leftIndex] < right[rightIndex])) {
                array[arrayIndex++] = left[leftIndex++];
            } else {
                array[arrayIndex++] = right[rightIndex++];
            }
        }
    }
}
