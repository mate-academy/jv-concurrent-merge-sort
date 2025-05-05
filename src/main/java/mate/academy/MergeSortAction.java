package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final Integer THRESHOLD = 2;
    private static final Integer ZERO = 0;
    private static final Integer DIVIDER = 2;
    private int[] array;
    private int arrayLength;

    public MergeSortAction(int[] array) {
        this.array = array;
        this.arrayLength = array.length;
    }

    @Override
    protected void compute() {
        mergeSort(array, arrayLength);
    }

    private void mergeSort(int [] array, int length) {
        if (length < THRESHOLD) {
            return;
        }
        int middle = length / DIVIDER;
        int [] left = new int[middle];
        int [] right = new int[length - middle];
        for (int i = ZERO; i < middle; i++) {
            left[i] = array[i];
        }
        for (int j = middle; j < length; j++) {
            right[j - middle] = array[j];
        }
        mergeSort(left, left.length);
        mergeSort(right, right.length);
        merge(left, right, left.length, right.length, array);
    }

    private void merge(
            int [] left, int [] right, int leftLength, int rightLength, int [] result
    ) {
        int i = ZERO;
        int j = ZERO;
        int k = ZERO;
        while (i < leftLength && j < rightLength) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }

        while (i < leftLength) {
            result[k++] = left[i++];
        }

        while (j < rightLength) {
            result[k++] = right[j++];
        }
    }
}
