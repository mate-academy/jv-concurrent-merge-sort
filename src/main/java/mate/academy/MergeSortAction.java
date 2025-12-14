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

    @Override
    protected void compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            Arrays.sort(array, start, end);
            return;
        }
        int mid = start + length / 2;
        MergeSortAction leftTask = new MergeSortAction(array, start, mid);
        MergeSortAction rightTask = new MergeSortAction(array, mid, end);
        invokeAll(leftTask, rightTask);
        merge(mid);
    }

    private void merge(int mid) {
        int length = end - start;
        int[] temp = new int[length];

        int i = start;
        int j = mid;
        int k = 0;

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

        System.arraycopy(temp, 0, array, start, length);
    }
}
