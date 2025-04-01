package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;

    public MergeSortAction(int[] array) {
        this.array = array;
        this.left = 0;
        this.right = array.length;
    }

    public MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (right - left <= 1) {
            return;
        }

        int mid = (left + right) / 2;

        MergeSortAction leftTask = new MergeSortAction(array, left, mid);
        MergeSortAction rightTask = new MergeSortAction(array, mid, right);

        invokeAll(leftTask, rightTask);

        merge(left, mid, right);
    }

    private void merge(int left, int mid, int right) {
        int[] temp = new int[right - left];
        int i = left;
        int j = mid;
        int k = 0;

        while (i < mid && j < right) {
            temp[k++] = (array[i] <= array[j]) ? array[i++] : array[j++];
        }
        while (i < mid) {
            temp[k++] = array[i++];
        }
        while (j < right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }
}
