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

        int mid = array.length / 2;
        int[] leftArray = Arrays.copyOfRange(array, 0, mid);
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
        int mainIdx = 0;
        int leftIdx = 0;
        int rightIdx = 0;

        while (leftIdx < left.length && rightIdx < right.length) {
            if (left[leftIdx] < right[rightIdx]) {
                array[mainIdx++] = left[leftIdx++];
            } else {
                array[mainIdx++] = right[rightIdx++];
            }
        }

        while (leftIdx < left.length) {
            array[mainIdx++] = left[leftIdx++];
        }

        while (rightIdx < right.length) {
            array[mainIdx++] = right[rightIdx++];
        }
    }
}
