package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= THRESHOLD) {
            Arrays.sort(array);
        } else {
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
    }

    private void mergeSortedArrays(int[] array, int[] leftArray, int[] rightArray) {
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
