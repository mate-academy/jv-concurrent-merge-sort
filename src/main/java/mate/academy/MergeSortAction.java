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

            MergeSortAction leftTask = new MergeSortAction(left);
            MergeSortAction rightTask = new MergeSortAction(right);

            invokeAll(leftTask, rightTask);

            merge(array, left, right);
        }
    }

    private void merge(int [] array, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length || j < right.length) {
            if (i < left.length && (j >= right.length || left[i] <= right[j])) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
    }
}
