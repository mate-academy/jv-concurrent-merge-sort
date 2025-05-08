package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= THRESHOLD) {
            Arrays.sort(array);
        } else {
            int middle = array.length / 2;
            int[] left = Arrays.copyOfRange(array, 0, middle);
            int[] right = Arrays.copyOfRange(array, middle, array.length);
            MergeSortAction leftTask = new MergeSortAction(left);
            MergeSortAction rightTask = new MergeSortAction(right);
            invokeAll(leftTask, rightTask);
            merge(left, right, array);
        }
    }

    private void merge(int[] left, int[] right, int[] result) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            result[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }
        if (i < left.length) {
            System.arraycopy(left, i, result, k, left.length - i);
        }
        if (j < right.length) {
            System.arraycopy(right, j, result, k, right.length - j);
        }
    }
}
