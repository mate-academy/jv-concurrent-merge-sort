package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;
    private static final int THRESHOLD = 16;

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
        if (left >= right) {
            return;
        }

        if (right - left < THRESHOLD) {
            insertionSort(array, left, right);
            return;
        }

        int mid = left + ((right - left) / 2);
        MergeSortAction leftTask = new MergeSortAction(array, left, mid);
        MergeSortAction rightTask = new MergeSortAction(array, mid + 1, right);

        invokeAll(leftTask, rightTask);
        merge(array, left, mid, right);
    }

    private void merge(int[] array, int left, int mid, int right) {
        int[] temp = new int[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            temp[k++] = (array[i] <= array[j]) ? array[i++] : array[j++];
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= left && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}
