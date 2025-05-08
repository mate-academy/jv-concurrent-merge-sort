package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int threshold;
    private final int[] array;
    private final int leftIndex;
    private final int rightIndex;

    public MergeSortAction(int[] array) {
        this(array, 2);
    }

    public MergeSortAction(int[] array, int threshold) {
        this(array, 0, array.length - 1, threshold);
    }

    private MergeSortAction(int[] array, int leftIndex, int rightIndex, int threshold) {
        this.array = array;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
        this.threshold = threshold;
    }

    @Override
    protected void compute() {
        if (leftIndex < rightIndex && (rightIndex - leftIndex + 1) > threshold) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            MergeSortAction leftTask =
                    new MergeSortAction(array, leftIndex, middleIndex, threshold);
            MergeSortAction rightTask =
                    new MergeSortAction(array, middleIndex + 1, rightIndex, threshold);
            leftTask.fork();
            rightTask.fork();
            leftTask.join();
            rightTask.join();
            merge(array, leftIndex, middleIndex, rightIndex);
        } else {
            sort(array, leftIndex, rightIndex);
        }
    }

    private void sort(int[] array, int leftIndex, int rightIndex) {
        if (leftIndex < rightIndex) {
            int middleIndex = (leftIndex + rightIndex) / 2;
            sort(array, leftIndex, middleIndex);
            sort(array, middleIndex + 1, rightIndex);
            merge(array, leftIndex, middleIndex, rightIndex);
        }
    }

    private void merge(int[] array, int leftIndex, int middleIndex, int rightIndex) {
        int mergedArrayLength = rightIndex - leftIndex + 1;
        int[] mergedArray = new int[mergedArrayLength];

        int leftSubarrayIndex = leftIndex;
        int rightSubarrayIndex = middleIndex + 1;
        int mergedSubarrayIndex = 0;

        while (leftSubarrayIndex <= middleIndex || rightSubarrayIndex <= rightIndex) {
            if (leftSubarrayIndex > middleIndex) {
                mergedArray[mergedSubarrayIndex++] = array[rightSubarrayIndex++];
            } else if (rightSubarrayIndex > rightIndex) {
                mergedArray[mergedSubarrayIndex++] = array[leftSubarrayIndex++];
            } else if (array[leftSubarrayIndex] < array[rightSubarrayIndex]) {
                mergedArray[mergedSubarrayIndex++] = array[leftSubarrayIndex++];
            } else {
                mergedArray[mergedSubarrayIndex++] = array[rightSubarrayIndex++];
            }
        }
        System.arraycopy(mergedArray, 0, array, leftIndex, mergedArrayLength);
    }
}
