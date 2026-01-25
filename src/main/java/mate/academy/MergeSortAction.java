package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private static final int THRESHOLD = 1;

    private final int[] array;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if (end - start > THRESHOLD) {
            int mid = (start + end) / 2;
            MergeSortAction left = new MergeSortAction(array, start, mid);
            MergeSortAction right = new MergeSortAction(array, mid, end);
            left.fork();
            right.fork();
            left.join();
            right.join();
            merge(start, mid, end);
        }
    }

    private void merge(int start, int mid, int end) {
        int left = start;
        int right = mid;
        int[] merged = new int[end - start];
        int i = 0;

        while (left < mid && right < end) {
            if (array[left] <= array[right]) {
                merged[i++] = array[left++];
            } else {
                merged[i++] = array[right++];
            }
        }
        while (left < mid) {
            merged[i++] = array[left++];
        }
        while (right < end) {
            merged[i++] = array[right++];
        }

        System.arraycopy(merged, 0, array, start, merged.length);
    }
}
