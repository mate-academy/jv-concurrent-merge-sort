package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction extends RecursiveTask<int[]> {
    private static final long THRESHOLD = 10;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = Arrays.copyOfRange(array, 0, array.length);
    }

    @Override
    protected int[] compute() {
        int[] result = new int[array.length];
        int start = 0;
        int end = array.length;
        if (array.length <= THRESHOLD) {
            System.arraycopy(array, start, result, start, end);
            Arrays.sort(result);
        } else {
            int midPoint = start + (end - start) / 2;
            int[] leftArray = Arrays.copyOfRange(array, start, midPoint);
            int[] rightArray = Arrays.copyOfRange(array, midPoint, end);
            MergeSortAction leftAction = new MergeSortAction(leftArray);
            MergeSortAction rightAction = new MergeSortAction(rightArray);
            leftAction.fork();
            int[] rightResult = rightAction.compute();
            int[] leftResult = leftAction.join();
            return merge(leftResult, rightResult);
        }
        return result;
    }

    private int[] merge(int[] leftResult, int[] rightResult) {
        int[] result = new int[leftResult.length + rightResult.length];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < leftResult.length && j < rightResult.length) {
            if (leftResult[i] <= rightResult[j]) {
                result[k++] = leftResult[i++];
            } else {
                result[k++] = rightResult[j++];
            }
        }
        System.arraycopy(leftResult, i, result, k, leftResult.length - i);
        System.arraycopy(rightResult, j, result, k, rightResult.length - j);
        return result;
    }
}
