package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 3;
    private final int[] array;
    private final int end;
    private final int start;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            bubbleSort(array);
        } else {
            int mid = start + (end - start) / 2;
            MergeSortAction leftAction = new MergeSortAction(array, start, mid);
            MergeSortAction rightAction = new MergeSortAction(array, mid, end);

            invokeAll(leftAction, rightAction);
            merge(start, mid, end);
        }
    }

    private void merge(int start, int mid, int end) {
        int[] left = new int[mid - start];
        int[] right = new int[end - mid];

        System.arraycopy(array, start, left, 0, left.length);
        System.arraycopy(array, mid, right, 0, right.length);

        int leftIndex = 0;
        int rightIndex = 0;
        int marker = start;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                array[marker++] = left[leftIndex++];
            } else {
                array[marker++] = right[rightIndex++];
            }
        }
        while (leftIndex < left.length) {
            array[marker++] = left[leftIndex++];
        }
        while (rightIndex < right.length) {
            array[marker++] = right[rightIndex++];
        }
    }

    private void bubbleSort(int[] array) {
        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                if (array[i - 1] > array[i]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }
}
