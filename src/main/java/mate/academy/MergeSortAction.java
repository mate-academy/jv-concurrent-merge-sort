package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start < THRESHOLD) {
            return;
        }
        int middle = start + (end - start) / 2;
        int[] leftPart = Arrays.copyOfRange(array, start, middle);
        int[] rightPart = Arrays.copyOfRange(array, middle, end);
        MergeSortAction leftAction = new MergeSortAction(leftPart);
        MergeSortAction rightAction = new MergeSortAction(rightPart);

        leftAction.fork();
        rightAction.compute();
        leftAction.join();

        merge(array, leftPart, rightPart);
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
