package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 16; // Поріг для маленьких підмасивів
    private final int[] array;
    private final int first;
    private final int last;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int first, int last) {
        this.array = array;
        this.first = first;
        this.last = last;
    }

    @Override
    protected void compute() {
        if (last - first <= THRESHOLD) {
            Arrays.sort(array, first, last);
        } else {
            int middle = first + (last - first) / 2;

            MergeSortAction leftTask = new MergeSortAction(array, first, middle);
            MergeSortAction rightTask = new MergeSortAction(array, middle, last);

            invokeAll(leftTask, rightTask);
        }
    }
}
