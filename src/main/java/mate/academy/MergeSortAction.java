package mate.academy;

import static java.util.Arrays.copyOfRange;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private static final int START_VALUE = 0;
    private final int[] array;
    private final int start;
    private final int length;
    private final int middle;

    public MergeSortAction(int[] array) {
        this.array = array;
        start = START_VALUE;
        length = array.length;
        middle = length / THRESHOLD;
    }

    @Override
    protected void compute() {
        if (length < THRESHOLD) {
            return;
        }

        int[] partOne = copyOfRange(array, start, middle);
        int[] partTwo = copyOfRange(array, middle, length);

        MergeSortAction actionOne = new MergeSortAction(partOne);
        MergeSortAction actionTwo = new MergeSortAction(partTwo);

        invokeAll(actionOne, actionTwo);

        merge(partOne, partTwo);
    }

    private void merge(int[] partOne, int[] partTwo) {
        int i = START_VALUE;
        int j = START_VALUE;
        while (i < partOne.length && j < partTwo.length) {
            array[i + j] = partOne[i] > partTwo[j]
                    ? partTwo[j++]
                    : partOne[i++];
        }
        while (i < partOne.length) {
            array[i + j] = partOne[i++];
        }
        while (j < partTwo.length) {
            array[i + j] = partTwo[j++];
        }
    }
}
