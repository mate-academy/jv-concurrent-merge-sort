package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction  extends RecursiveTask<Void> {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected Void compute() {
        if (array.length > 1) {
            int mid = array.length / 2;

            int[] leftArray = Arrays.copyOfRange(array, 0, mid);
            int[] rightArray = Arrays.copyOfRange(array, mid, array.length);

            MergeSortAction left = new MergeSortAction(leftArray);
            MergeSortAction right = new MergeSortAction(rightArray);

            invokeAll(left, right);

            merge(leftArray, rightArray);
        }
        return null;
    }

    private void merge(int[] leftArray, int[] rightArray) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }

        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }
    }
}
