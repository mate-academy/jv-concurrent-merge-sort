package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length > 1) {
            int[] firstArray = Arrays.copyOfRange(array, 0, array.length / 2);
            int[] secondArray = Arrays.copyOfRange(array, array.length / 2, array.length);
            invokeAll(new MergeSortAction(firstArray), new MergeSortAction(secondArray));
            joine(firstArray, secondArray);
        }
    }

    private void joine(int[] firstArray, int[] secondArray) {
        int i = 0;
        int j = 0;
        while (i < firstArray.length && j < secondArray.length) {
            if (firstArray[i] < secondArray[j]) {
                array[i + j] = firstArray[i++];
            } else {
                array[i + j] = secondArray[j++];
            }
        }
        while (i < firstArray.length) {
            array[i + j] = firstArray[i++];
        }
        while (j < secondArray.length) {
            array[i + j] = secondArray[j++];
        }
    }
}
