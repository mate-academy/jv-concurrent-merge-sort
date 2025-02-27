package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    public static final int THRESHOLD = 2;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (array.length == 0) {
            return;
        }
        if (end - start < THRESHOLD) {
            sortTwoElements(start, end);
        } else {
            int middle = start + (end - start) / 2;
            MergeSortAction leftTask = new MergeSortAction(array, start, middle);
            MergeSortAction rightTask = new MergeSortAction(array, middle + 1, end);
            invokeAll(leftTask, rightTask);
            merge(start, middle + 1, end);
        }
    }

    private void sortTwoElements(int start, int end) {
        if (array[start] > array[end]) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
        }
    }

    private void merge(int start, int middle, int end) {
        int length = end - start + 1;
        int[] temp = new int[length];
        int leftIndex = start;
        int rightIndex = middle;
        for (int i = 0; i < length; i++) {
            if (leftIndex < middle && rightIndex <= end) {
                temp[i] = array[leftIndex] < array[rightIndex]
                        ? array[leftIndex++]
                        : array[rightIndex++];
            } else {
                temp[i] = leftIndex < middle ? array[leftIndex++] : array[rightIndex++];
            }
        }
        for (int i = start, j = 0; i <= end; i++, j++) {
            array[i] = temp[j];
        }
    }
}
