package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int low;
    private final int high;

    public MergeSortAction(int[] array, int low, int high) {
        this.array = array;
        this.low = low;
        this.high = high;
    }

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    @Override
    protected void compute() {
        if (low < high) {
            int mid = low + (high - low) / 2;
            MergeSortAction leftTask = new MergeSortAction(array, low, mid);
            MergeSortAction rightTask = new MergeSortAction(array, mid + 1, high);
            invokeAll(leftTask, rightTask);
            merge(array, low, mid, high);
        }
    }

    private void merge(int[] array, int low, int mid, int high) {
        int[] leftArray = Arrays.copyOfRange(array, low, mid + 1);
        int[] rightArray = Arrays.copyOfRange(array, mid + 1, high + 1);
        int leftIndex = 0;
        int rightIndex = 0;
        int mergeIndex = low;
        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                array[mergeIndex++] = leftArray[leftIndex++];
            } else {
                array[mergeIndex++] = rightArray[rightIndex++];
            }
        }
        while (leftIndex < leftArray.length) {
            array[mergeIndex++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            array[mergeIndex++] = rightArray[rightIndex++];
        }
    }
}
