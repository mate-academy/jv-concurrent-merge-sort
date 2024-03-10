package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length < 2) {
            return;
        }

        int lowLeft = 0;
        int length = array.length;
        int mid = length / 2;
        int lowRight = length - mid;

        int[] left = new int[mid];
        System.arraycopy(array, lowLeft, left, lowLeft, mid);

        int[] right = new int[lowRight];
        System.arraycopy(array, mid, right, lowLeft, length - mid);

        MergeSortAction actionLeft = new MergeSortAction(left);
        MergeSortAction actionRight = new MergeSortAction(right);

        actionLeft.fork();
        actionRight.fork();

        actionLeft.join();
        actionRight.join();

        mergeArrays(left, right, mid, lowRight);
    }

    private void mergeArrays(int[] left, int[] right, int lowLeft, int lowRight) {
        int i = 0;
        int k = 0;
        int j = 0;
        while (i < lowLeft && j < lowRight) {
            if (left[i] <= right[j]) {
                array[k++] = left[i++];
            } else {
                array[k++] = right[j++];
            }
        }
        while (i < lowLeft) {
            array[k++] = left[i++];
        }
        while (j < lowRight) {
            array[k++] = right[j++];
        }
    }
}
