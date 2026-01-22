package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction extends RecursiveTask<int[]> {
    private static final int THRESHOLD = 4;

    private int[] array;
    private int start;
    private int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected int[] compute() {
        if (end - start <= THRESHOLD) {
            return sort();
        } else {
            int middle = start + (end - start) / 2;
            int[] left = Arrays.copyOfRange(array, start, middle);
            int[] right = Arrays.copyOfRange(array, middle, end);
            MergeSortAction leftTask = new MergeSortAction(left);
            MergeSortAction rightTask = new MergeSortAction(right);
            leftTask.fork();

            int[] rightArr = rightTask.compute();
            int[] leftArr = leftTask.join();

            return mergeTwoArrays(leftArr, rightArr);
        }
    }

    private int[] sort() {
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    int temp = array[i];
                    array[i] = array[j];
                    array[j] = temp;
                }
            }
        }
        return array;
    }

    private int[] mergeTwoArrays(int[] array1, int[] array2) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < array1.length && j < array2.length) {
            array[k++] = (array1[i] < array2[j]) ? array1[i++] : array2[j++];
        }

        while (i < array1.length) {
            array[k++] = array1[i++];
        }
        while (j < array2.length) {
            array[k++] = array2[j++];
        }
        return array;
    }

}
