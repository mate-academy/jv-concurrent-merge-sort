package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 10;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    @Override
    protected void compute() {
        if (end - start + 1 <= THRESHOLD) {
            insertionSort(array, start, end);
        } else {
            int mid = start + (end - start) / 2;

            MergeSortAction leftTask = new MergeSortAction(array, start, mid);
            MergeSortAction rightTask = new MergeSortAction(array, mid + 1, end);

            invokeAll(leftTask, rightTask);

            merge(array, start, mid, end);
        }
    }

    private void insertionSort(int[] array, int start, int end) {
        for (int i = start + 1; i <= end; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= start && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private void merge(int[] array, int start, int mid, int end) {
        int[] temp = new int[end - start + 1];
        int i = start;
        int j = mid + 1;
        int k = 0;

        while (i <= mid && j <= end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= mid) {
            temp[k++] = array[i++];
        }

        while (j <= end) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
