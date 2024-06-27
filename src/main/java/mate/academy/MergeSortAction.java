package mate.academy;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private int[] ints;
    private final int from;
    private final int to;

    public int[] getInts() {
        return ints;
    }

    private MergeSortAction(int[] massiv, int from, int to) {
        this.ints = massiv;
        this.from = from;
        this.to = to;
    }

    public MergeSortAction(int[] massiv) {
        this(massiv, 0, massiv.length);
    }

    @Override
    protected void compute() {
        if (ints.length >= THRESHOLD) {
            MergeSortAction leftAction = new MergeSortAction(Arrays.copyOfRange(ints, from, ints.length / 2));
            MergeSortAction rightAction = new MergeSortAction(Arrays.copyOfRange(ints, ints.length / 2, to));
            invokeAll(leftAction, rightAction);
            merge(leftAction.getInts(), rightAction.getInts());
        }
    }

    private void merge(int[] left, int[] right) {
        int i = 0;
        int j = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                ints[i + j] = left[i];
                i++;
            } else {
                ints[i + j] = right[j];
                j++;
            }
        }

        while (i < left.length) {
            this.ints[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            this.ints[i + j] = right[j];
            j++;
        }
    }

    private void merge(int from, int middle, int to) {
        int[] sortedMassiv = Arrays.copyOfRange(this.ints, from, middle);
        for (int i = 0, j = from, k = middle; i < sortedMassiv.length; j++) {
            this.ints[j] = (k == to || sortedMassiv[i] < this.ints[k]) ? sortedMassiv[i++] : this.ints[k++];
        }
    }
}
