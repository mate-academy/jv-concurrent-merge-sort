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
            int mid = array.length / 2;

            int[] left = new int[mid];
            System.arraycopy(array, 0, left, 0, mid);

            int[] right = new int[array.length - mid];
            System.arraycopy(array, mid, right, 0, array.length - mid);

            MergeSortAction actionLeft = new MergeSortAction(left);
            MergeSortAction actionRight = new MergeSortAction(right);

            actionLeft.fork();
            actionRight.fork();

            actionLeft.join();
            actionRight.join();

            merge(array, left, right);
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
