package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 16;

    private final int[] array;
    private final int left;
    private final int right;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    private MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        int size = right - left + 1;
        if (size <= THRESHOLD) {
            Arrays.sort(array, left, right + 1);
        } else {
            int mid = left + (right - left) / 2;

            MergeSortAction leftTask = new MergeSortAction(array, left, mid);
            MergeSortAction rightTask = new MergeSortAction(array, mid + 1, right);

            invokeAll(leftTask, rightTask);

            merge(left, mid, right);
        }
    }

    private void merge(int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;
        int[] leftArr = new int[n1];
        int[] rightArr = new int[n2];
        System.arraycopy(array, left, leftArr, 0, n1);
        System.arraycopy(array, mid + 1, rightArr, 0, n2);

        int i = 0;
        int j = 0;
        int k = left;

        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                array[k] = leftArr[i];
                i++;
            } else {
                array[k] = rightArr[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            array[k] = leftArr[i];
            i++;
            k++;
        }
        while (j < n2) {
            array[k] = rightArr[j];
            j++;
            k++;
        }
    }
}
