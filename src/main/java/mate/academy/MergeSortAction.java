package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 1;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        int start = 0;
        int workLoad = array.length;

        if (workLoad > THRESHOLD) {
            int middle = workLoad / 2;

            int[] first = Arrays.copyOfRange(array, start, middle);
            int[] second = Arrays.copyOfRange(array, middle, workLoad);

            MergeSortAction firstSort = new MergeSortAction(first);
            MergeSortAction secondSort = new MergeSortAction(second);

            invokeAll(firstSort, secondSort);
            merge(first, second);
        }
    }

    private void merge(int[] first, int[] second) {
        int firstIndex = 0;
        int secondIndex = 0;
        int arrayIndex = 0;

        while (firstIndex < first.length && secondIndex < second.length) {
            if (first[firstIndex] <= second[secondIndex]) {
                array[arrayIndex++] = first[firstIndex++];
            } else {
                array[arrayIndex++] = second[secondIndex++];
            }
        }

        while (firstIndex < first.length) {
            array[arrayIndex++] = first[firstIndex++];
        }

        while (secondIndex < second.length) {
            array[arrayIndex++] = second[secondIndex++];
        }
    }
}
