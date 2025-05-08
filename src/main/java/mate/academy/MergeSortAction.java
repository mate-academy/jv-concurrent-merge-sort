package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int length;

    public MergeSortAction(int[] array) {
        this(array, array.length);
    }

    private MergeSortAction(int[] array, int length) {
        this.array = array;
        this.length = length;
    }

    @Override
    protected void compute() {
        if (length < 2) {
            return;
        }
        int middle = length / 2;
        int[] l = new int[middle];
        int[] r = new int[length - middle];

        System.arraycopy(array, 0, l, 0, middle);
        System.arraycopy(array, middle, r, 0, length - middle);

        RecursiveAction leftAction = new MergeSortAction(l, middle);
        RecursiveAction rightAction = new MergeSortAction(r, length - middle);

        leftAction.fork();
        rightAction.fork();

        leftAction.join();
        rightAction.join();

        merge(l, r, middle, length - middle);
    }

    private void merge(int[] l, int[] r, int leftLength, int rightLength) {
        int leftIndex = 0;
        int rightIndex = 0;
        int index = 0;
        while (leftIndex < leftLength && rightIndex < rightLength) {
            if (l[leftIndex] <= r[rightIndex]) {
                array[index++] = l[leftIndex++];
            } else {
                array[index++] = r[rightIndex++];
            }
        }
        while (leftIndex < leftLength) {
            array[index++] = l[leftIndex++];
        }
        while (rightIndex < rightLength) {
            array[index++] = r[rightIndex++];
        }
    }
}
