package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        int n = array.length;
        if (n == 0 || n == 1) {
            return;
        }
        int[] left = Arrays.copyOfRange(array, 0, n / 2);
        int[] right = Arrays.copyOfRange(array, n / 2, n);
        MergeSortAction leftTask = new MergeSortAction(left);
        MergeSortAction rightTask = new MergeSortAction(right);
        leftTask.fork();
        rightTask.compute();
        leftTask.join();
        merge(array, left, right);
    }

    private void merge(int[] array, int[] left, int[] right) {
        int l = 0;
        int r = 0;
        while (l < left.length && r < right.length) {
            if (left[l] < right[r]) {
                array[l + r] = left[l++];
            } else {
                array[l + r] = right[r++];
            }
        }
        System.arraycopy(left, l, array, l + r, left.length - l);
        System.arraycopy(right, r, array, l + r, right.length - r);
    }
}
