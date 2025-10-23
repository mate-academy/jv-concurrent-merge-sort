package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 3;
    private int[] array;
    private int start;
    private int end;

    public MergeSortAction(int[] array) {
        this.array = array;
        this.start = 0;
        this.end = array.length;
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
            int middle = (end + start) / 2;

            MergeSortAction left = new MergeSortAction(array, start, middle);
            MergeSortAction right = new MergeSortAction(array, middle, end);

            left.fork();
            right.compute();
            left.join();

            merge(array, start, middle, end);
        }
    }

    private void merge(int[] array, int start, int middle, int end) {
        int[] left = Arrays.copyOfRange(array, start, middle);
        int[] right = Arrays.copyOfRange(array, middle, end);

        int i = 0;
        int j = 0;
        int k = start;

        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }

        while (i < left.length) {
            array[k++] = left[i++];
        }

        while (j < right.length) {
            array[k++] = right[j++];
        }
    }
}
