package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 3;
    private final int[] array;
    private final int length;
    private final int start;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int length, int start) {
        this.array = array;
        this.length = length;
        this.start = start;
    }

    @Override
    protected void compute() {
        if (length - start <= THRESHOLD) {
            Arrays.sort(array);
        } else {
            int mid = (start + length) / 2;
            MergeSortAction left = new MergeSortAction(array, start, mid);
            MergeSortAction right = new MergeSortAction(array, mid, length);

            invokeAll(left, right);
        }
    }
}
