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
        if (array.length > 1) {
            int[] leftArray = Arrays.copyOfRange(array, 0, array.length / 2);
            int[] rightArray = Arrays.copyOfRange(array, array.length / 2, array.length);
            MergeSortAction leftSortAction = new MergeSortAction(leftArray);
            MergeSortAction rightSortAction = new MergeSortAction(rightArray);
            invokeAll(leftSortAction, rightSortAction);
            merge(leftArray, rightArray);
        }
    }

    private void merge(int[] leftArray, int[] rightArray) {
        int i = 0;
        int j = 0;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] < rightArray[j]) {
                array[i + j] = leftArray[i];
                i++;
            } else {
                array[i + j] = rightArray[j];
                j++;
            }
        }
        while (i < leftArray.length) {
            array[i + j] = leftArray[i++];
        }
        while (j < rightArray.length) {
            array[i + j] = rightArray[j++];
        }
    }
}
