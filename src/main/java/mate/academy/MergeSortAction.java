package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
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
        if (end - start <= 1) {
            return;
        }

        int middle = start + (end - start) / 2;

        MergeSortAction leftTask = new MergeSortAction(array, start, middle);
        MergeSortAction rightTask = new MergeSortAction(array, middle, end);

        invokeAll(leftTask, rightTask);

        merge(start, middle, end);
    }

    private void merge(int start, int middle, int end) {
        int[] temp = new int[end - start];

        int leftIndex = start;
        int rightIndex = middle;
        int tempIndex = 0;

        while (leftIndex < middle && rightIndex < end) {
            if (array[leftIndex] <= array[rightIndex]) {
                temp[tempIndex++] = array[leftIndex++];
            } else {
                temp[tempIndex++] = array[rightIndex++];
            }
        }

        while (leftIndex < middle) {
            temp[tempIndex++] = array[leftIndex++];
        }

        while (rightIndex < end) {
            temp[tempIndex++] = array[rightIndex++];
        }

        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
