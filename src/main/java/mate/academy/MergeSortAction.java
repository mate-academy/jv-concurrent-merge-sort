package mate.academy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length > 1) {
            int mid = array.length / 2;

            int[] leftArray = Arrays.copyOfRange(array, 0, mid);
            int[] rightArray = Arrays.copyOfRange(array, mid, array.length);

            List<MergeSortAction> subtask = createSubtask(leftArray, rightArray);

            for (RecursiveAction action : subtask) {
                action.fork();
                action.join();
            }

            merge(leftArray, rightArray, array);
        }
    }

    private List<MergeSortAction> createSubtask(int[] leftArray, int[] rightArray) {
        return List.of(new MergeSortAction(leftArray), new MergeSortAction(rightArray));
    }

    private int[] merge(int[] left, int[] right, int[] result) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
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
        return result;
    }
}
