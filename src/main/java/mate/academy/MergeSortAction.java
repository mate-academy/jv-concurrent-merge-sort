package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private int[] array;
    private int start;
    private int end;
    private final int threshold = 2;

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
        int size = end - start;
        if (end - start <= threshold) {
            if (size == 2 && array[start] > array[start + 1]) {
                int temp = array[start];
                array[start] = array[start + 1];
                array[start + 1] = temp;
            }
        } else {
            int mid = start + (end - start) / 2;
            MergeSortAction left = new MergeSortAction(array, start, mid);
            MergeSortAction right = new MergeSortAction(array, mid, end);

            invokeAll(left, right);
            merge(start, mid, end);
        }
    }

    private void merge(int start, int mid, int end) {
        int[] temp = new int[end - start];
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
        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
