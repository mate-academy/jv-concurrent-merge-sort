package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction extends RecursiveTask<int[]> {
    private static final int THRESHOLD = 4;

    private final int[] array;

    private final int start;

    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected int[] compute() {
        if (end - start < THRESHOLD) {
            Arrays.sort(array);
            return this.array;
        } else {
            int middle = start + (end - start) / 2;
            MergeSortAction left = new MergeSortAction(array, start, middle);
            MergeSortAction right = new MergeSortAction(array, middle + 1, end);

            left.fork();
            right.fork();

            int[] leftResult = left.join();
            int[] rightResult = right.join();

            int[] result = new int[leftResult.length + rightResult.length];
            System.arraycopy(leftResult, 0, result, 0, leftResult.length);
            System.arraycopy(rightResult, 0, result, leftResult.length, rightResult.length);
            Arrays.sort(result);
            return result;
        }
    }
}
