package mate.academy;

import java.util.Arrays; 
import java.util.Objects;
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
        Objects.requireNonNull(array, "array must not be null");
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start > THRESHOLD) {
            int middle = start + (end - start) / 2;
            RecursiveAction left = new MergeSortAction(array, start, middle);
            RecursiveAction right = new MergeSortAction(array, middle, end);

            invokeAll(left, right);
            merge(array, start, middle, end);
        } else {
            sortPartArray(array, start, end);
        }
    }

    private void sortPartArray(int[] array, int start, int end) {
        Arrays.sort(array, start, end);
    }

    private void merge(int[] array, int start, int middle, int end) {
        int[] temp = new int[end - start];
        int i = start;
        int j = middle;
        int k = 0;
        while (i < middle && j < end) {
            if (array[i] < array[j]) {
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
