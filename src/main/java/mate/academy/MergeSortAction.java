package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
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
        if (end - start > 1) {
            int middle = start + (end - start) / 2;

            MergeSortAction leftTask = new MergeSortAction(array, start, middle);
            MergeSortAction rightTask = new MergeSortAction(array, middle, end);

            leftTask.invoke();
            rightTask.invoke();

            merge(start, middle, end);
        }
    }

    private void merge(int start, int middle, int end) {
        int[] arr = new int[end - start];
        int i = start;
        int j = middle;
        int k = 0;

        while (i < middle && j < end) {
            if (array[i] <= array[j]) {
                arr[k++] = array[i++];
            } else {
                arr[k++] = array[j++];
            }
        }

        while (i < middle) {
            arr[k++] = array[i++];
        }

        while (j < end) {
            arr[k++] = array[j++];
        }

        System.arraycopy(arr, 0, array, start, arr.length);
    }
}
