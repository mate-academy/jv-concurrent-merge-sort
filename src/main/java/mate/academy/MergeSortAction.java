package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= 1) {
            return;
        }

        int mid = array.length / 2;

        int[] leftArray = new int[mid];
        int[] rightArray = new int[array.length - mid];

        System.arraycopy(array, 0, leftArray, 0, leftArray.length);
        System.arraycopy(array, mid, rightArray, 0, rightArray.length);

        MergeSortAction leftTask = new MergeSortAction(leftArray);
        MergeSortAction rightTask = new MergeSortAction(rightArray);

        invokeAll(leftTask, rightTask);

        merge(leftArray, rightArray, array);
    }

    private void merge(int[] left, int[] right, int[] result) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] < right[rightIndex]) {
                result[resultIndex++] = left[leftIndex++];
            } else {
                result[resultIndex++] = right[rightIndex++];
            }
        }

        while (leftIndex < left.length) {
            result[resultIndex++] = left[leftIndex++];
        }

        while (rightIndex < right.length) {
            result[resultIndex++] = right[rightIndex++];
        }
    }
}

