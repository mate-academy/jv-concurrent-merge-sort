package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length > 1) {
            int middlePoint = array.length / 2;
            int[] leftArray = Arrays.copyOfRange(array, 0, middlePoint);
            int[] rightArray = Arrays.copyOfRange(array, middlePoint, array.length);
            MergeSortAction leftAction = new MergeSortAction(leftArray);
            MergeSortAction rightAction = new MergeSortAction(rightArray);
            leftAction.fork();
            rightAction.fork();
            leftAction.join();
            rightAction.join();
            merge(leftArray, rightArray);
        }
    }

    private void merge(int[] leftArray, int[] rightArray) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;
        int[] result = new int[leftArray.length + rightArray.length];
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                result[resultIndex++] = leftArray[leftIndex++];
            } else {
                result[resultIndex++] = rightArray[rightIndex++];
            }
        }

        while (leftIndex < leftArray.length) {
            result[resultIndex++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            result[resultIndex++] = rightArray[rightIndex++];
        }

        System.arraycopy(result, 0, array, 0, result.length);
    }
}
