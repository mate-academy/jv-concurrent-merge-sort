package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction extends RecursiveTask<Void> {
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    public MergeSortAction(int[] array, int start, int end) {
        super();
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Void compute() {
        if (end - start <= 1) {
            return null;
        }

        int mid = (start + end) / 2;
        MergeSortAction leftTask = new MergeSortAction(array, start, mid);
        MergeSortAction rightTask = new MergeSortAction(array, mid, end);

        invokeAll(leftTask, rightTask);

        merge(start, mid, end);
        return null;
    }

    private void merge(int start, int mid, int end) {
        int[] left = Arrays.copyOfRange(array, start, mid);
        int[] right = Arrays.copyOfRange(array, mid, end);

        int i = 0;
        int j = 0;
        int k = start;

        while (i < left.length && j < right.length) {
            array[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }
        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
