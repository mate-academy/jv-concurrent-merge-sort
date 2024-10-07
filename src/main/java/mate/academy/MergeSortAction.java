package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 500;

    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            sequentialSort(array, start, end);
        } else {
            int mid = start + (end - start) / 2;

            MergeSortAction leftTask = new MergeSortAction(array, start, mid);
            MergeSortAction rightTask = new MergeSortAction(array, mid, end);

            invokeAll(leftTask, rightTask);

            merge(array, start, mid, end);
        }
    }

    private void sequentialSort(int[] array, int start, int end) {
        java.util.Arrays.sort(array, start, end);
    }

    private void merge(int[] array, int start, int mid, int end) {
        // Check if merging is necessary
        if (array[mid - 1] <= array[mid]) {
            return; // Subarrays are already sorted
        }

        int[] temp = new int[end - start];
        int i = start;
        int j = mid;
        int k = 0;

        while (i < mid && j < end) {
            temp[k++] = (array[i] <= array[j]) ? array[i++] : array[j++];
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
