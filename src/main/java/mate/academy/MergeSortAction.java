package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int right;
    private final int left;

    public MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    @Override
    protected void compute() {
        if (left < right) {
            int middle = (left + right) / 2;
            invokeAll(
                    new MergeSortAction(array, left, middle),
                    new MergeSortAction(array, middle + 1, right)
            );
            merge(array, left, middle, right);
        }
    }

    private void merge(int[] array, int left, int middle, int right) {
        int[] leftArray = new int[middle - left + 1];
        int[] rightArray = new int[right - middle];

        System.arraycopy(array, left, leftArray, 0, leftArray.length);
        System.arraycopy(array, middle + 1, rightArray, 0, rightArray.length);

        int i = 0;
        int j = 0;
        int k = left;

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
