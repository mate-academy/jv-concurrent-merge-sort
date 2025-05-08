package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private static final int MERGE_CYCLES_BEGGING_INDEX = 0;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length < THRESHOLD) {
            Arrays.sort(array);
        } else {

            int mid = array.length / 2;

            int[] left = Arrays.copyOfRange(array, MERGE_CYCLES_BEGGING_INDEX, mid);
            int[] right = Arrays.copyOfRange(array, mid, array.length);

            MergeSortAction leftTask = new MergeSortAction(left);
            MergeSortAction rightTask = new MergeSortAction(right);

            invokeAll(leftTask, rightTask);

            merge(array, left, right);
        }
    }

    private void merge(int[] array, int[] left, int[] right) {
        int i = MERGE_CYCLES_BEGGING_INDEX;
        int j = MERGE_CYCLES_BEGGING_INDEX;
        int k = MERGE_CYCLES_BEGGING_INDEX;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
