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
        int length = end - start + 1;

        if (length <= THRESHOLD) {
            return sortSegment();
        }

        int mid = start + (end - start) / 2;

        MergeSortAction leftTask = new MergeSortAction(array, start, mid);
        MergeSortAction rightTask = new MergeSortAction(array, mid + 1, end);

        leftTask.fork();
        int[] rightArray = rightTask.compute();
        int[] leftArray = leftTask.join();

        int[] merged = merge(leftArray, rightArray);

        System.arraycopy(merged, 0, array, start, merged.length);

        return merged;
    }

    private int[] sortSegment() {
        return Arrays.stream(array, start, end + 1)
                .sorted()
                .toArray();
    }

    private int[] merge(int[] left, int[] right) {
        int[] result = new int[left.length + right.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                result[k++] = left[i++];
            } else {
                result[k++] = right[j++];
            }
        }
        while (i < left.length) {
            result[k++] = left[i++];
        }
        while (j < right.length) {
            result[k++] = right[j++];
        }
        return result;
    }
}
