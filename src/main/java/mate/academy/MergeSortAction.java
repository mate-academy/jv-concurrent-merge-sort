package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;
    private final int[] array;
    private final int startPoint;
    private final int finishPoint;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int startPoint, int finishPoint) {
        this.array = array;
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }

    @Override
    protected void compute() {
        if (finishPoint - startPoint <= THRESHOLD) {
            if (finishPoint - startPoint == THRESHOLD
                    && array[startPoint] > array[startPoint + 1]) {
                int temp = array[startPoint];
                array[startPoint] = array[startPoint + 1];
                array[startPoint + 1] = temp;
            }
        } else {
            int middle = startPoint + (finishPoint - startPoint) / 2;
            MergeSortAction left = new MergeSortAction(array, startPoint, middle);
            MergeSortAction right = new MergeSortAction(array, middle, finishPoint);
            invokeAll(left, right);
            merge(startPoint, middle, finishPoint);
        }
    }

    private void merge(int start, int mid, int end) {
        int[] temp = new int[end - start];
        int i = start;
        int j = mid;
        int k = 0;

        while (i < mid && j < end) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i < mid) {
            temp[k++] = array[i++];
        }

        while (j < end) {
            temp[k++] = array[j++];
        }
        System.arraycopy(temp, 0, array, start, temp.length);
    }
}
