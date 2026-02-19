package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length < 10) {
            Arrays.sort(array);
        } else {
            int middle = array.length / 2;
            int[] left = new int[array.length / 2];
            for (int i = 0; i < middle; i++) {
                left[i] = array[i];
            }

            int[] right = new int[array.length - middle];
            for (int i = 0; i < right.length; i++) {
                right[i] = array[i + middle];
            }

            MergeSortAction leftAction = new MergeSortAction(left);
            MergeSortAction rightAction = new MergeSortAction(right);

            invokeAll(leftAction, rightAction);
            merge(left, right, array);
        }
    }

    private void merge(int[] left, int[] right, int[] array) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                array[k] = left[i];
                i++;
                k++;
            } else {
                array[k] = right[j];
                j++;
                k++;
            }
        }

        while (j < right.length) {
            array[k] = right[j];
            j++;
            k++;
        }

        while (i < left.length) {
            array[k] = left[i];
            i++;
            k++;
        }
    }
}
