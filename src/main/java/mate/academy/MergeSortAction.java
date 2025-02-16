package mate.academy;

import java.util.Arrays;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction extends RecursiveTask<int[]> {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected int[] compute() {
        if (array.length < 2) {
            return array;
        }
        int[] leftArray = Arrays.copyOfRange(array, 0, array.length / 2);
        int[] rightArray = Arrays.copyOfRange(array, array.length / 2, array.length);
        MergeSortAction leftAction = new MergeSortAction(leftArray);
        MergeSortAction rightAction = new MergeSortAction(rightArray);
        ForkJoinTask<int[]> leftFork = leftAction.fork();
        int[] right = rightAction.compute();
        int[] left = leftFork.join();
        int[] merged = merge(left, right);
        System.out.println(Arrays.toString(array));
        System.arraycopy(merged, 0, array, 0, array.length);
        System.out.println(Arrays.toString(array));
        return this.array;
    }

    private int[] merge(int[] left, int[] right) {
        int leftCursor = 0;
        int rightCursor = 0;
        int[] merged = new int[left.length + right.length];
        for (int i = 0; i < merged.length; i++) {
            if (leftCursor < left.length
                    && (rightCursor >= right.length
                    || left[leftCursor] < right[rightCursor])) {
                merged[i] = left[leftCursor];
                leftCursor++;
            } else {
                merged[i] = right[rightCursor];
                rightCursor++;
            }
        }
        return merged;
    }
}
