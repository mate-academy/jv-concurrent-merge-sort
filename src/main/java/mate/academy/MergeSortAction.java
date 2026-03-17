package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 2;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(0, array.length, array);
    }

    private MergeSortAction(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            sort();
        } else {
            MergeSortAction left = new MergeSortAction(start, (start + end) / 2, array);
            MergeSortAction right = new MergeSortAction((start + end) / 2, end, array);
            invokeAll(left, right);
            merge((start + end) / 2);
        }
    }

    private void sort() {
        for (int i = start + 1; i < end; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= start && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private void merge(int middle) {
        int[] halfArray = new int[end - start];
        int i = start;
        int j = middle;
        int k = 0;

        while (i < middle && j < end) {
            if (array[i] <= array[j]) {
                halfArray[k++] = array[i++];
            } else {
                halfArray[k++] = array[j++];
            }
        }
        while (i < middle) {
            halfArray[k++] = array[i++];
        }
        while (j < end) {
            halfArray[k++] = array[j++];
        }

        System.arraycopy(halfArray, 0, array, start, halfArray.length);
    }
}
