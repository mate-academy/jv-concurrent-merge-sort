package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 3; // при бажанні підберіть емпірично
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
        if (end - start <= THRESHOLD) {
            Arrays.sort(array, start, end);
            return;
        }

        int middle = start + (end - start) / 2;
        MergeSortAction left = new MergeSortAction(array, start, middle);
        MergeSortAction right = new MergeSortAction(array, middle, end);

        invokeAll(left, right);

        merge(array, start, middle, end);
    }

    private void merge(int[] array, int start, int middle, int end) {
        int[] tmp = Arrays.copyOfRange(array, start, end);
        int leftIndex = 0;
        int rightIndex = middle - start;
        int leftEnd = middle - start;
        int rightEnd = end - start;
        int dest = start;

        while (leftIndex < leftEnd && rightIndex < rightEnd) {
            if (tmp[leftIndex] <= tmp[rightIndex]) {
                array[dest++] = tmp[leftIndex++];
            } else {
                array[dest++] = tmp[rightIndex++];
            }
        }

        while (leftIndex < leftEnd) {
            array[dest++] = tmp[leftIndex++];
        }

        while (rightIndex < rightEnd) {
            array[dest++] = tmp[rightIndex++];
        }
    }
}
