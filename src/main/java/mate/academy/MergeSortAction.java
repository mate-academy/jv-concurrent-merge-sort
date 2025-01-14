package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private int start;
    private int end;

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
        if (end - start <= 1) {
            return;
        } else {
            int middle = start + (end - start) / 2;
            MergeSortAction leftTask = new MergeSortAction(array, start, middle);
            MergeSortAction rightTask = new MergeSortAction(array, middle, end);

            invokeAll(leftTask, rightTask);

            merge(start, middle, end);
        }
    }

    private void merge(int start, int middle, int end) {
        int[] temp = new int[end - start];
        int i = start;
        int j = middle;
        int k = 0;

        while (i < middle && j < end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < middle) {
            temp[k++] = array[i++];
        }
        while (j < end) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
