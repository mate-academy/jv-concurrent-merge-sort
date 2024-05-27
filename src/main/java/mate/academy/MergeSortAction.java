package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;

    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= THRESHOLD) {
            Arrays.sort(array);
        } else {
            int middle = array.length / 2;
            int[] leftArray = Arrays.copyOfRange(array, 0, middle);
            int[] rightArray = Arrays.copyOfRange(array, middle, array.length);

            MergeSortAction left = new MergeSortAction(leftArray);
            MergeSortAction right = new MergeSortAction(rightArray);
            invokeAll(left, right);

            mergeArrays(leftArray, rightArray);
        }
    }

    private void mergeArrays(int[] leftArray, int[] rightArray) {
        int[] mergedArray = new int[leftArray.length + rightArray.length];
        int left = 0;
        int right = 0;
        int mergeIndex = 0;

        while (left < leftArray.length && right < rightArray.length) {
            if (leftArray[left] <= rightArray[right]) {
                mergedArray[mergeIndex++] = leftArray[left++];
            } else {
                mergedArray[mergeIndex++] = rightArray[right++];
            }
        }

        while (left < leftArray.length) {
            mergedArray[mergeIndex++] = leftArray[left++];
        }

        while (right < rightArray.length) {
            mergedArray[mergeIndex++] = rightArray[right++];
        }

        System.arraycopy(mergedArray, 0, array, 0, mergedArray.length);
    }
}
