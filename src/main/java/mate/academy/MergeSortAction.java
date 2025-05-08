package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (start < end) {
            int mid = start + (end - start) / 2;
            MergeSortAction leftTask = new MergeSortAction(array, start, mid);
            MergeSortAction rightTask = new MergeSortAction(array, mid + 1, end);
            invokeAll(leftTask, rightTask);
            merge(start, mid, end);
        }
    }

    private void merge(int start, int mid, int end) {
        int[] tempArray = new int[array.length];
        int i = start;
        int j = mid + 1;
        int k = start;
        while (i <= mid && j <= end) {
            if (array[i] <= array[j]) {
                tempArray[k++] = array[i++];
            } else {
                tempArray[k++] = array[j++];
            }
        }
        while (i <= mid) {
            tempArray[k++] = array[i++];
        }
        while (j <= end) {
            tempArray[k++] = array[j++];
        }
        System.arraycopy(tempArray, start, array, start, end - start + 1);
    }
}
