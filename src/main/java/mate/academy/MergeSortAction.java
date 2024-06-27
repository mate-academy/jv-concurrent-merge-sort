package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private final int[] ints;
    private final int from;
    private final int to;

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
        if (to - from >= THRESHOLD) {
            int middle = (from + to) >>> 1;
            invokeAll(new MergeSortAction(ints, from, middle),
                      new MergeSortAction(ints, middle, to));
            merge(from, middle, to);
        } else {
            Arrays.sort(ints, from, to);
        }
    }

    private void merge(int from, int middle, int to) {
        int[] copyInts = Arrays.copyOfRange(ints, from, middle);
        for (int i = 0, j = from, k = middle; i < copyInts.length; j++) {
            ints[j] = (k == to || copyInts[i] < ints[k]) ? copyInts[i++] : ints[k++];
        }
    }
}
