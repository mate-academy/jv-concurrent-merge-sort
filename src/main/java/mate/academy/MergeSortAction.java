package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 10;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= 1) {
            return;
        }

        if (array.length < THRESHOLD) {
            Arrays.sort(array);
            return;
        }

        int mid = array.length / 2;

        int[] leftArray = Arrays.copyOfRange(array, 0, mid);
        int[] rightArray = Arrays.copyOfRange(array, mid, array.length);

        MergeSortAction leftTask = new MergeSortAction(leftArray);
        MergeSortAction rightTask = new MergeSortAction(rightArray);

        invokeAll(leftTask, rightTask);

        merge(leftArray, rightArray);
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
