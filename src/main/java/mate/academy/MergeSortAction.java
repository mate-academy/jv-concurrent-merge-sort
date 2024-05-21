package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private final int start;
    private final int finish;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int start, int finish) {
        this.array = array;
        this.start = start;
        this.finish = finish;
    }

    @Override
    protected void compute() {
        if (finish - start <= THRESHOLD) {
            Arrays.sort(array, start, finish);
        } else {
            int middlePoint = start + (finish - start) / 2;
            MergeSortAction left = new MergeSortAction(array, start, middlePoint);
            MergeSortAction right = new MergeSortAction(array, middlePoint, finish);
            left.fork();
            right.fork();
            left.join();
            right.join();
            merge(array, start, middlePoint, finish);
        }
    }

    private void merge(int[] array, int start, int middle, int finish) {
        int[] left = Arrays.copyOfRange(array, start, middle);
        int[] right = Arrays.copyOfRange(array, middle, finish);
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
