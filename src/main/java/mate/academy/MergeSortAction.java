package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int left, int right) {
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
        int[] temp = Arrays.copyOfRange(array, left, right);
        int i = 0;
        int j = mid - left;
        int k = left;

        while (i < mid - left && j < right - left) {
            if (temp[i] <= temp[j]) {
                array[k++] = temp[i++];
            } else {
                array[k++] = temp[j++];
            }
        }
        while (i < mid - left) {
            array[k++] = temp[i++];
        }
        while (j < right - left) {
            array[k++] = temp[j++];
        }
    }
}

