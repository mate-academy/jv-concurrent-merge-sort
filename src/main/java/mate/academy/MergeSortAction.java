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
        if (array.length > 1) {
            int mid = array.length / 2;
            int[] left = Arrays.copyOfRange(array, 0, mid);
            int[] right = Arrays.copyOfRange(array, mid, array.length);

            MergeSortAction mergeSortActionLeft = new MergeSortAction(left);
            MergeSortAction mergeSortActionRight = new MergeSortAction(right);
            invokeAll(mergeSortActionLeft, mergeSortActionRight);
            merge(left, right);
        }
    }

    private void merge(int[] left, int[] right) {
        int l = 0;
        int r = 0;
        int current = 0;
        while (l < left.length && r < right.length) {
            if (left[l] <= right[r]) {
                array[current] = left[l];
                l++;
            } else {
                array[current] = right[r];
                r++;
            }
            current++;
        }

        while (l < left.length) {
            array[current] = left[l];
            current++;
            l++;
        }
        while (r < right.length) {
            array[current] = right[r];
            current++;
            r++;
        }
    }
}
