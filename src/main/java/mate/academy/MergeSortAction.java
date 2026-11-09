package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int leftIndex;
    private final int rightIndex;

    public MergeSortAction(int[] array) {
        this.array = array;
        leftIndex = 0;
        rightIndex = array.length - 1;
    }

    public MergeSortAction(int[] array, int leftIndex, int rightIndex) {
        this.array = array;
        this.leftIndex = leftIndex;
        this.rightIndex = rightIndex;
    }

    @Override
    protected void compute() {
        if (leftIndex >= rightIndex) {
            return;
        }
        int middle = leftIndex + ((rightIndex - leftIndex) / 2);
        MergeSortAction leftMergeSort = new MergeSortAction(array, leftIndex, middle);
        MergeSortAction rightMergeSort = new MergeSortAction(array, middle + 1, rightIndex);
        leftMergeSort.fork();
        rightMergeSort.compute();
        leftMergeSort.join();
        merge(middle);
    }

    private void merge(int middle) {
        int[] temp = new int[rightIndex - leftIndex + 1];

        int i = leftIndex;
        int j = middle + 1;
        int k = 0;

        while (i <= middle && j <= rightIndex) {
            if (array[i] < array[j]) {
                temp[k] = array[i];
                k++;
                i++;
            } else {
                temp[k] = array[j];
                k++;
                j++;
            }
        }
        while (i <= middle) {
            temp[k] = array[i];
            k++;
            i++;
        }
        while (j <= rightIndex) {
            temp[k] = array[j];
            k++;
            j++;
        }
        System.arraycopy(temp, 0, array, leftIndex, temp.length);
    }
}
