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
        if (end - start >= THRESHOLD) {
            int middle = (start + end) >>> 1;
            invokeAll(new MergeSortAction(array, start, middle),
                    new MergeSortAction(array, middle, end));
            merge(start, middle, end);
        } else {
            Arrays.sort(array, start, end);
        }
    }

    private void merge(int start, int middle, int end) {
        int[] buffer = Arrays.copyOfRange(array, start, middle);
        for (int i = start, leftIndex = 0, rightIndex = middle; leftIndex < buffer.length; i++) {
            if (rightIndex == end || buffer[leftIndex] < array[rightIndex]) {
                array[i] = buffer[leftIndex];
                leftIndex++;
            } else {
                array[i] = array[rightIndex];
                rightIndex++;
            }
        }
    }
}
