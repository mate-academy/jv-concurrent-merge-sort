package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= 1) {
            return;
        }

        int mid = array.length / 2;
        int[] leftSubArray = Arrays.copyOfRange(array, 0, mid);
        int[] rightSubArray = Arrays.copyOfRange(array, mid, array.length);

        MergeSortAction leftSortAction = new MergeSortAction(leftSubArray);
        MergeSortAction rightSortAction = new MergeSortAction(rightSubArray);

        invokeAll(leftSortAction, rightSortAction);

        merge(leftSubArray, rightSubArray);
    }

    private void merge(int[] leftSubArray, int[] rightSubArray) {
        int i = 0, j = 0, k = 0;

        while (i < leftSubArray.length && j < rightSubArray.length) {
            array[k++] = (leftSubArray[i] <= rightSubArray[j]) ? leftSubArray[i++] : rightSubArray[j++];
        }

        while (i < leftSubArray.length) {
            array[k++] = leftSubArray[i++];
        }

        while (j < rightSubArray.length) {
            array[k++] = rightSubArray[j++];
        }
    }
}
