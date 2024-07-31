package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private int left;
    private int right;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    private MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }
    // 3 9 1 0 2 8
    // mid = 2
    // 3 9 1
    // 3 9
    // 1
    // 3
    // 9

    // 0 2 8 ->
    @Override
    protected void compute() {
        if (left < right) {
            int mid = (left + right) / 2;
            MergeSortAction leftTask = new MergeSortAction(array, left, mid);
            MergeSortAction rightTask = new MergeSortAction(array, mid + 1, right);
            invokeAll(leftTask, rightTask);
            merge(array, left, mid, right);
        }
    }

    private void merge(int[] array, int left, int mid, int right) {
        int sizeOfLeft = mid - left + 1;
        int sizeOfRight = right - mid;
        int[] leftArray = new int[sizeOfLeft];
        int[] rightArray = new int[sizeOfRight];
        System.arraycopy(array, left, leftArray, 0, sizeOfLeft);
        System.arraycopy(array, mid + 1, rightArray, 0, sizeOfRight);
        int i = 0;
        int j = 0;
        int k = left;
        while (i < sizeOfLeft && j < sizeOfRight) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }
        while (i < sizeOfLeft) {
            array[k] = leftArray[i];
            i++;
            k++;
        }
        while (j < sizeOfRight) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
