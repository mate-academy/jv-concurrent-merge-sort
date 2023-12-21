package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private int[] arr;

    public MergeSortAction(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected void compute() {
        if (arr.length < 2) {
            return;
        }
        var left = Arrays.copyOfRange(arr, 0, arr.length / 2);
        var right = Arrays.copyOfRange(arr, arr.length / 2, arr.length);

        var leftSortAction = new MergeSortAction(left);
        var rightSortAction = new MergeSortAction(right);
        leftSortAction.fork();
        rightSortAction.compute();
        leftSortAction.join();
        merge(arr, left, right);
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }
}
