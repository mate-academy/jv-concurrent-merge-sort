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
        if (array.length <= THRESHOLD) {
            return;
        }

        int middleIndex = array.length / 2;
        int[] left = Arrays.copyOfRange(array, 0, middleIndex);
        int[] right = Arrays.copyOfRange(array, middleIndex, array.length);

        MergeSortAction leftSortAction = new MergeSortAction(left);
        MergeSortAction rightSortAction = new MergeSortAction(right);

        invokeAll(leftSortAction, rightSortAction);

        merge(left, right);
    }

    private void merge(int[] left, int[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            array[mergeIndex++] = (left[leftIndex] <= right[rightIndex])
                    ? left[leftIndex++] : right[rightIndex++];
        }

        while (leftIndex < left.length) {
            array[mergeIndex++] = left[leftIndex++];
        }

        while (rightIndex < right.length) {
            array[mergeIndex++] = right[rightIndex++];
        }
    }
}
