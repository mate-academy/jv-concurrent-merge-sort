package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int TWO = 2;
    private static final int ZERO = 0;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= TWO) {
            Arrays.sort(array);
        } else {
            int midl = array.length / TWO;
            MergeSortAction firstPart = new MergeSortAction(Arrays.copyOfRange(array, ZERO, midl));
            MergeSortAction secondPart =
                    new MergeSortAction(Arrays.copyOfRange(array, midl, array.length));
            firstPart.fork();
            secondPart.fork();
            firstPart.join();
            secondPart.join();
            merge(firstPart.array, secondPart.array);
        }
    }

    private void merge(int[] firstArray, int[] secondArray) {
        int i = ZERO;
        int j = ZERO;
        int a = ZERO;
        while (i < firstArray.length && j < secondArray.length) {
            if (firstArray[i] < secondArray[j]) {
                array[a++] = firstArray[i++];
            } else {
                array[a++] = secondArray[j++];
            }
        }
        while (i < firstArray.length) {
            array[a++] = firstArray[i++];
        }
        while (j < secondArray.length) {
            array[a++] = secondArray[j++];
        }
    }
}
