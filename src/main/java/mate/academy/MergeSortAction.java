package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    private MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int middle = left + (right - left) / 2;
            invokeAll(
                    new MergeSortAction(array, left, middle),
                    new MergeSortAction(array, middle + 1, right)
            );
            merge(left, middle, right);
        }
    }

    private void merge(int left, int middle, int right) {
        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        int[] leftArray = new int[leftSize];
        int[] rightArray = new int[rightSize];

        System.arraycopy(array, left, leftArray, 0, leftSize);
        System.arraycopy(array, middle + 1, rightArray, 0, rightSize);

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftSize && j < rightSize) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < leftSize) {
            array[k++] = leftArray[i++];
        }

        while (j < rightSize) {
            array[k++] = rightArray[j++];
        }
    }
}
