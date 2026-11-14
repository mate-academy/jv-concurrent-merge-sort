package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
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
        if (left >= right) {
            return;
        } else {
            int middle = (left + right) / 2;
            MergeSortAction leftPart = new MergeSortAction(array, left, middle);
            MergeSortAction rightPart = new MergeSortAction(array, middle + 1, right);
            invokeAll(leftPart, rightPart);
            merge(left, middle, right);
        }
    }

    private void merge(int left, int middle, int right) {
        int[] temporary = new int[right - left + 1];
        int i = left;
        int j = middle + 1;
        int k = 0;
        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                temporary[k] = array[i];
                i++;
            } else {
                temporary[k] = array[j];
                j++;
            }
            k++;
        }
        while (i <= middle) {
            temporary[k++] = array[i++];
        }
        while (j <= right) {
            temporary[k++] = array[j++];
        }
        for (int index = 0; index < temporary.length; index++) {
            array[left + index] = temporary[index];
        }
    }
}
