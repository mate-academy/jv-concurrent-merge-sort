package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    public static final int THRESHOLD = 2;
    public static final int START_INDEX = 0;
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

        int mid = array.length / 2;

        int[] leftArray = Arrays.copyOfRange(array, START_INDEX, mid);
        int[] rightArray = Arrays.copyOfRange(array, mid, array.length);

        MergeSortAction leftAction = new MergeSortAction(leftArray);
        MergeSortAction rightAction = new MergeSortAction(rightArray);

        leftAction.fork();
        rightAction.fork();

        rightAction.compute();
        leftAction.join();

        merge(leftArray, rightArray);
    }

    private void merge(int[] left, int[] right) {
        int mainIdx = START_INDEX;
        int leftLength = left.length;
        int leftIdx = START_INDEX;
        int rightLength = right.length;
        int rightIdx = START_INDEX;

        while (leftIdx < leftLength && rightIdx < rightLength) {
            if (left[leftIdx] < right[rightIdx]) {
                array[mainIdx++] = left[leftIdx++];
            } else {
                array[mainIdx++] = right[rightIdx++];
            }
        }

        while (leftIdx < leftLength) {
            array[mainIdx++] = left[leftIdx++];
        }

        while (rightIdx < rightLength) {
            array[mainIdx++] = right[rightIdx++];
        }
    }
}
