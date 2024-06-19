package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length < 2) {
            return;
        }

        int middle = array.length / 2;
        MergeSortAction leftTask = new MergeSortAction(Arrays.copyOfRange(array, 0, middle));
        MergeSortAction rightTask =
                new MergeSortAction(Arrays.copyOfRange(array, middle, array.length));

        invokeAll(leftTask, rightTask);

        merge(array, leftTask.array, rightTask.array);
    }

    private void merge(int[] result, int[] left, int[] right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;
        
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
    }
}
