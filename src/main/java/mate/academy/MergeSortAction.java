package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 7;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start <= THRESHOLD) {
            Arrays.sort(array, start, end);
        } else {
            int middle = start + (end - start) / 2;
            MergeSortAction leftAction = new MergeSortAction(array, start, middle);
            MergeSortAction rightAction = new MergeSortAction(array, middle, end);
            invokeAll(leftAction, rightAction);
            merge(array, start, middle, end);
        }
    }

    private void merge(int[] array, int start, int middle, int end) {
        int[] temp = new int[end - start];
        int i = start;
        int j = middle;
        int k = 0;

        while (i < middle && j < end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }
        while (i < middle) {
            temp[k++] = array[i++];
        }
        while (j < end) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
