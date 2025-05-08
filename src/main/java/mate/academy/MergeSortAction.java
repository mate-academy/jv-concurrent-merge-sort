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
        int[] leftSubArray = Arrays.copyOfRange(array, 0, middleIndex);
        int[] rightSubArray = Arrays.copyOfRange(array, middleIndex, array.length);

        MergeSortAction leftSortAction = new MergeSortAction(leftSubArray);
        MergeSortAction rightSortAction = new MergeSortAction(rightSubArray);

        invokeAll(leftSortAction, rightSortAction);

        merge(leftSubArray, rightSubArray);
    }

    private void merge(int[] leftSubArray, int[] rightSubArray) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = 0;

        while (leftIndex < leftSubArray.length && rightIndex < rightSubArray.length) {
            array[mergeIndex++] = (leftSubArray[leftIndex] <= rightSubArray[rightIndex])
                    ? leftSubArray[leftIndex++] : rightSubArray[rightIndex++];
        }

        while (leftIndex < leftSubArray.length) {
            array[mergeIndex++] = leftSubArray[leftIndex++];
        }

        while (rightIndex < rightSubArray.length) {
            array[mergeIndex++] = rightSubArray[rightIndex++];
        }
    }
}
