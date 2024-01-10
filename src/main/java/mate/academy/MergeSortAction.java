package mate.academy;

import java.util.concurrent.RecursiveTask;
import java.util.stream.IntStream;

public class MergeSortAction extends RecursiveTask<Integer[]> {
    private static final int THRESHOLD = 5;
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
    protected Integer[] compute() {
        int length = end - start;

        if (length <= THRESHOLD) {
            insertionSort(array);
            return IntStream.of(array).boxed().toArray(Integer[]::new);
        }

        int middle = start + (end - start) / 2;

        MergeSortAction leftTask = new MergeSortAction(array, start, middle);
        MergeSortAction rightTask = new MergeSortAction(array, middle, end);

        leftTask.fork();

        Integer[] rightResult = rightTask.compute();
        Integer[] leftResult = leftTask.join();

        return mergeArrays(leftResult, rightResult);
    }

    public static Integer[] mergeArrays(Integer[] array1, Integer[] array2) {
        Integer[] result = new Integer[array1.length + array2.length];

        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);

        return result;
    }

    private static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }

            array[j + 1] = key;
        }
    }
}
