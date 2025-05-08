package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 1;
    private int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length > THRESHOLD) {
            int middle = array.length / 2;
            int[] leftPart = Arrays.copyOfRange(array, 0, middle);
            int[] rightPart = Arrays.copyOfRange(array, middle, array.length);

            MergeSortAction leftAction = new MergeSortAction(leftPart);
            MergeSortAction rightAction = new MergeSortAction(rightPart);

            invokeAll(leftAction, rightAction);

            merge(leftPart, rightPart);
        }
    }

    private void merge(int[] leftPart, int[] rightPart) {
        int leftIndex = 0;
        int rightIndex = 0;
        int arrayIndex = 0;

        int leftLength = leftPart.length;
        int rightLength = rightPart.length;

        while (leftIndex < leftLength || rightIndex < rightLength) {
            if (leftIndex < leftLength
                    && (rightIndex >= rightLength
                    || leftPart[leftIndex] < rightPart[rightIndex])) {
                array[arrayIndex++] = leftPart[leftIndex++];
            } else {
                array[arrayIndex++] = rightPart[rightIndex++];
            }
        }
    }
}
