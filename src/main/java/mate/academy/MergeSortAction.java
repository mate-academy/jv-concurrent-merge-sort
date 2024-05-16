package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (isSorted()) {
            return;
        }
        int midIndex = array.length / 2;
        int[] leftArray = createCopy(0, midIndex);
        int[] rightArray = createCopy(midIndex, array.length);
        invokeAll(new MergeSortAction(leftArray), new MergeSortAction(rightArray));
        merge(leftArray, rightArray);
    }

    private void merge(int[] leftArray, int[] rightArray) {
        int index = 0;
        int leftIndex = 0;
        int rightIndex = 0;
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                array[index++] = leftArray[leftIndex++];
            } else {
                array[index++] = rightArray[rightIndex++];
            }
        }
        while (leftIndex < leftArray.length) {
            array[index++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            array[index++] = rightArray[rightIndex++];
        }
    }

    private int[] createCopy(int startIndex, int endIndex) {
        int[] result = new int[endIndex - startIndex];
        System.arraycopy(array, startIndex, result, 0, endIndex - startIndex);
        return result;
    }

    private boolean isSorted() {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }
}
