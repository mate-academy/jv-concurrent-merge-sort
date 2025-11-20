package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 4;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    /**
     * The main computation performed by this task.
     */
    @Override
    protected void compute() {
        // base case
        int size = end - start;
        if (size > THRESHOLD) {
            int mid = start + size / 2;
            MergeSortAction left = new MergeSortAction(array, start, mid);
            MergeSortAction right = new MergeSortAction(array, mid, end);
            invokeAll(left, right);
            merge(array, start, mid, end);
        } else {
            Arrays.sort(array, start, end);
        }
    }

    private void merge(int[] array, int start, int mid, int end) {
        int i = start;
        int j = mid;
        int k = 0;
        int[] temp = new int[end - start];

        while (i < mid && j < end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < mid) {
            temp[k++] = array[i++];
        }

        while (j < end) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
