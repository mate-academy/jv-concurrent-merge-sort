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
        var left = Arrays.copyOfRange(array, 0, array.length / 2);
        var right = Arrays.copyOfRange(array, array.length / 2, array.length);
        invokeAll(new MergeSortAction(left), new MergeSortAction(right));
        merge(left, right);
    }

    private void merge(int[] left, int[] right) {
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                array[i + j] = left[i];
                i++;
            } else {
                array[i + j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            array[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            array[i + j] = right[j];
            j++;
        }
    }
}
