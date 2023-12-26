package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 1;
    private final int[] array;
    private final int length;

    public MergeSortAction(int[] array) {
        this.array = array;
        length = array.length;
    }

    @Override
    protected void compute() {
        if (array.length <= THRESHOLD) {
            return;
        }
        int[] left = Arrays.copyOfRange(array, 0, length / 2);
        int[] right = Arrays.copyOfRange(array, length / 2, length);

        MergeSortAction leftAction = new MergeSortAction(left);
        MergeSortAction rightAction = new MergeSortAction(right);
        leftAction.fork();
        rightAction.compute();
        leftAction.join();
        merge(array, left, right);
    }

    private void merge(int[] array, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                array[i + j] = left[i++];
            } else {
                array[i + j] = right[j++];
            }
        }
        System.arraycopy(left, i, array, i + j, left.length - i);
        System.arraycopy(right, j, array, i + j, right.length - j);
    }
}
