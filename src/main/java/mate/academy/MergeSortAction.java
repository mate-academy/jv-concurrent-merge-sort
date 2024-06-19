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
            merge(left, right);
        }
    }

    private void merge(int[] left, int[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int arrayIndex = 0;

        int leftLength = left.length;
        int rightLength = right.length;

        while (leftIndex < leftLength || rightIndex < rightLength) {
            if (leftIndex < leftLength
                    && (rightIndex >= rightLength
                    || left[leftIndex] < right[rightIndex])) {
                array[arrayIndex++] = left[leftIndex++];
            } else {
                array[arrayIndex++] = right[rightIndex++];
            }
        }
    }
}
