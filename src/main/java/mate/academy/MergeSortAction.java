package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 1;
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

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            Arrays.sort(array, start, end);
        } else {
            int middle = start + (end - start) / 2;
            MergeSortAction left = new MergeSortAction(array, start, middle);
            MergeSortAction right = new MergeSortAction(array, middle, end);
            invokeAll(left, right);
            merge(start, middle, end);
        }
    }

    private void merge(int start, int middle, int end) {
        int[] tmp = new int[end - start];
        int i = start;
        int j = middle;
        int k = 0;
        while (i < middle && j < end) {
            if (array[i] <= array[j]) {
                tmp[k++] = array[i++];
            } else {
                tmp[k++] = array[j++];
            }
        }
        while (i < middle) {
            tmp[k++] = array[i++];
        }
        while (j < end) {
            tmp[k++] = array[j++];
        }
        System.arraycopy(tmp, 0, array, start, k);
    }
}
