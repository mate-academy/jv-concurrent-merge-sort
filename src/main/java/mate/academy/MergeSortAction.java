package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 10;
    private final int[] array;
    private final int fromIndex;
    private final int toIndex;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int fromIndex, int toIndex) {
        this.array = array;
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
    }

    @Override
    protected void compute() {
        if (toIndex - fromIndex <= THRESHOLD) {
            insertionSort(array, fromIndex, toIndex);
        } else {
            int mid = (fromIndex + toIndex) / 2;
            MergeSortAction leftAction = new MergeSortAction(array, fromIndex, mid);
            MergeSortAction rightAction = new MergeSortAction(array, mid, toIndex);

            invokeAll(leftAction, rightAction);

            merge(array, fromIndex, mid, toIndex);
        }
    }

    private void insertionSort(int[] array, int fromIndex, int toIndex) {
        for (int i = fromIndex + 1; i < toIndex; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= fromIndex && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }

    private void merge(int[] array, int fromIndex, int mid, int toIndex) {
        int[] temp = new int[toIndex - fromIndex];
        int i = fromIndex;
        int j = mid;
        int k = 0;

        while (i < mid && j < toIndex) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < mid) {
            temp[k++] = array[i++];
        }

        while (j < toIndex) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, fromIndex, temp.length);
    }
}
