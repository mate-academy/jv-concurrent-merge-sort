package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= THRESHOLD) {
            Arrays.sort(array);
            return;
        }
        int middle = array.length / 2;
        int[] leftArray = Arrays.copyOfRange(array, 0, middle);
        int[] rightArray = Arrays.copyOfRange(array, middle, array.length);
        MergeSortAction leftAction = new MergeSortAction(leftArray);
        MergeSortAction rightAction = new MergeSortAction(rightArray);

        leftAction.fork();
        rightAction.fork();
        rightAction.join();
        leftAction.join();

        mergeSortedArrays(array, leftArray, rightArray);
    }

    private void mergeSortedArrays(int[] array, int[] leftArray, int[] rightArray) {
        int i = 0;
        int j = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] < rightArray[j]) {
                array[i+j] = leftArray[i++];
            } else {
                array[i+j] = rightArray[j++];
            }
        }

        while (i < leftArray.length) {
            array[i+j] = leftArray[i++];
        }

        while (j < rightArray.length) {
            array[i+j] = rightArray[j++];
        }
    }
}
