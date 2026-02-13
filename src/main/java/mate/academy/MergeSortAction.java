package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private int[] array;
    private int start;
    private int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start > THRESHOLD) {
            int middle = start + (end - start) / 2;
            MergeSortAction left = new MergeSortAction(array, start, middle);
            MergeSortAction right = new MergeSortAction(array, middle, end);

            invokeAll(left, right);
            merge(middle);
        } else {
            Arrays.sort(array, start, end);
        }
    }

    private void merge(int middle) {
        int i = start;
        int j = middle;
        int k = 0;
        int[] result = new int[end - start];

        while (i < middle && j < end) {
            result[k++] = array[i] <= array[j] ? array[i++] : array[j++];
        }
        while (i < middle) {
            result[k++] = array[i++];
        }
        while (j < end) {
            result[k++] = array[j++];
        }

        System.arraycopy(result, 0, array, start, result.length);
    }
}
