package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private int[] array;
    private int first;
    private int last;

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
        if (last - first < THRESHOLD) {
            Arrays.sort(array);
        } else {
            int middle = first + (last - first) / 2;

            MergeSortAction firstTask = new MergeSortAction(array, first, middle);
            MergeSortAction secondTask = new MergeSortAction(array, middle, last);

            firstTask.fork();
            secondTask.fork();

            firstTask.join();
            secondTask.join();
        }
    }
}
