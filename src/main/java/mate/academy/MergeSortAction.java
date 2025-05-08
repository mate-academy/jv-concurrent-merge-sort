package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    public void compute() {
        if (array.length < 2) {
            return;
        }
        int middle = array.length / 2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[array.length - middle];
        System.arraycopy(array, 0, leftArray, 0, middle);
        System.arraycopy(array, middle, rightArray, 0, array.length - middle);
        MergeSortAction mergeSortAction1 = new MergeSortAction(leftArray);
        MergeSortAction mergeSortAction2 = new MergeSortAction(rightArray);
        mergeSortAction1.fork();
        mergeSortAction2.fork();
        mergeSortAction1.join();
        mergeSortAction2.join();
        merge(leftArray, rightArray);
    }

    private void merge(int[] leftArray, int[] rightArray) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] < rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }
        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }
        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }
    }
}
