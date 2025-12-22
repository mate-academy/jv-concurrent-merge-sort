package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    public MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            Arrays.sort(array, start, end);
        } else {
            int middle = start + (end - start) / 2;

            MergeSortAction leftAction = new MergeSortAction(array, start, middle);
            MergeSortAction rightAction = new MergeSortAction(array, middle, end);

            leftAction.fork();
            rightAction.compute();
            leftAction.join();
            merge(start, middle, end);
        }
    }

    private void merge(int start, int middle, int end) {
        int[] temp = Arrays.copyOfRange(array, start, end);
        int left = 0;
        int right = middle - start;
        int write = start;

        while (left < middle - start && right < end - start) {
            if (temp[left] <= temp[right]) {
                array[write] = temp[left];
                left++;
            } else {
                array[write] = temp[right];
                right++;
            }
            write++;
        }

        while (left < middle - start) {
            array[write] = temp[left];
            left++;
            write++;
        }

        while (right < end - start) {
            array[write] = temp[right];
            right++;
            write++;
        }
    }
}
