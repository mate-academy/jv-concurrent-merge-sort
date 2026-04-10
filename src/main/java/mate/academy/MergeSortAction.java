package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int DEFAULT_THRESHOLD = 4;
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
        if (toIndex - fromIndex <= DEFAULT_THRESHOLD) {
            insertionSort(array, fromIndex, toIndex);
            return;
        }

        int mid = fromIndex + (toIndex - fromIndex) / 2;
        MergeSortAction leftTask = new MergeSortAction(array, fromIndex, mid);
        MergeSortAction rightTask = new MergeSortAction(array, mid, toIndex);

        invokeAll(leftTask, rightTask);
        merge(array, fromIndex, mid, toIndex);
    }

    private void insertionSort(int[] array, int fromIndex, int toIndex) {
        for (int i = fromIndex + 1; i < toIndex; i++) {
            int current = array[i];
            int j = i - 1;

            while (j >= fromIndex && array[j] > current) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = current;
        }
    }

    private void merge(int[] array, int fromIndex, int mid, int toIndex) {
        int[] temp = new int[toIndex - fromIndex];
        int left = fromIndex;
        int right = mid;
        int index = 0;

        while (left < mid && right < toIndex) {
            if (array[left] <= array[right]) {
                temp[index++] = array[left++];
            } else {
                temp[index++] = array[right++];
            }
        }

        while (left < mid) {
            temp[index++] = array[left++];
        }

        while (right < toIndex) {
            temp[index++] = array[right++];
        }

        System.arraycopy(temp, 0, array, fromIndex, temp.length);
    }
}
