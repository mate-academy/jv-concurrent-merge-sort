package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 10;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length <= 1) {
            return;
        }

        if (array.length < THRESHOLD) {
            Arrays.sort(array);
            return;
        }

        int middle = array.length / 2;
        int[] leftPart = Arrays.copyOfRange(array, 0, middle);
        int[] rightPart = Arrays.copyOfRange(array, middle, array.length);

        MergeSortAction leftTask = new MergeSortAction(leftPart);
        MergeSortAction rightTask = new MergeSortAction(rightPart);

        invokeAll(leftTask, rightTask);

        merge(leftPart, rightPart, array);
    }

    private void merge(int[] leftPart, int[] rightPart, int[] result) {

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < leftPart.length && j < rightPart.length) {
            if (leftPart[i] <= rightPart[j]) {
                result[k++] = leftPart[i++];
            } else {
                result[k++] = rightPart[j++];
            }
        }

        while (i < leftPart.length) {
            result[k++] = leftPart[i++];
        }

        while (j < rightPart.length) {
            result[k++] = rightPart[j++];
        }
    }
}
