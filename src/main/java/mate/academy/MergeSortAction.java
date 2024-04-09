package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 1;
    private int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length > THRESHOLD) {
            int middle = array.length / 2;
            int[] leftArray = new int[middle];
            int[] rightArray = new int[array.length - middle];
            System.arraycopy(array, 0, leftArray, 0, leftArray.length);
            System.arraycopy(array, middle, rightArray, 0, rightArray.length);
            MergeSortAction leftSubTask = new MergeSortAction(leftArray);
            MergeSortAction rightSubTask = new MergeSortAction(rightArray);
            leftSubTask.fork().join();
            rightSubTask.fork().join();
            merge(array, leftArray, rightArray);
        }
    }

    private void merge(int[] array, int[] leftArray, int[] rightArray) {
        int leftIndex = 0;
        int rightIndex = 0;
        int mainArrayIndex = 0;

        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                array[mainArrayIndex] = leftArray[leftIndex++];
            } else {
                array[mainArrayIndex] = rightArray[rightIndex++];
            }
            mainArrayIndex++;
        }
        while (leftIndex < leftArray.length) {
            array[mainArrayIndex++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            array[mainArrayIndex++] = rightArray[rightIndex++];
        }
    }
}
