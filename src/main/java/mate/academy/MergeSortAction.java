package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= THRESHOLD) {
            sort(array);
        } else {
            int middle = array.length / 2;
            int[] leftArray = Arrays.copyOfRange(array, 0, middle);
            int[] rightArray = Arrays.copyOfRange(array, middle, array.length);

            MergeSortAction leftAction = new MergeSortAction(leftArray);
            MergeSortAction rightAction = new MergeSortAction(rightArray);
            leftAction.fork().join();
            rightAction.fork().join();

            combineSortedArrays(array, leftArray, rightArray);
        }
    }

    private void sort(int[] array) {
        Arrays.sort(array);
    }

    private void combineSortedArrays(int[] array, int[] leftArray, int[] rightArray) {
        int arrayPosition = 0;
        int leftArrayPosition = 0;
        int rightArrayPosition = 0;

        while (leftArrayPosition < leftArray.length && rightArrayPosition < rightArray.length) {
            if (leftArray[leftArrayPosition] <= rightArray[rightArrayPosition]) {
                array[arrayPosition++] = leftArray[leftArrayPosition++];
            } else {
                array[arrayPosition++] = rightArray[rightArrayPosition++];
            }
        }

        while (leftArrayPosition < leftArray.length) {
            array[arrayPosition++] = leftArray[leftArrayPosition++];
        }

        while (rightArrayPosition < rightArray.length) {
            array[arrayPosition++] = rightArray[rightArrayPosition++];
        }
    }
}
