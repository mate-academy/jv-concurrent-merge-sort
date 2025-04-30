package mate.academy;

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
            int[] sortedArray = mergeSort(array);
            System.arraycopy(sortedArray, 0, array, 0, array.length);
        }
    }

    private int[] mergeSort(int[] array) {
        if (array.length <= 1) {
            return array;
        }
        int middle = array.length / 2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[array.length - middle];
        System.arraycopy(array, 0, leftArray, 0, middle);
        System.arraycopy(array, middle, rightArray, 0, array.length - middle);

        MergeSortAction leftSort = new MergeSortAction(leftArray);
        MergeSortAction rightSort = new MergeSortAction(rightArray);

        leftSort.fork();
        rightSort.compute();
        leftSort.join();

        return merge(leftArray, rightArray);
    }

    private int[] merge(int[] leftArray, int[] rightArray) {
        int[] mergedArray = new int[leftArray.length + rightArray.length];
        int leftPointer = 0;
        int rightPointer = 0;
        int mergedIndex = 0;
        while (leftPointer < leftArray.length && rightPointer < rightArray.length) {
            if (leftArray[leftPointer] <= rightArray[rightPointer]) {
                mergedArray[mergedIndex++] = leftArray[leftPointer++];
            } else {
                mergedArray[mergedIndex++] = rightArray[rightPointer++];
            }
        }
        while (leftPointer < leftArray.length) {
            mergedArray[mergedIndex++] = leftArray[leftPointer++];
        }

        while (rightPointer < rightArray.length) {
            mergedArray[mergedIndex++] = rightArray[rightPointer++];
        }
        return mergedArray;
    }
}
