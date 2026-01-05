package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private int left;
    private int right;

    public MergeSortAction(int[] array) {
        this.array = array;
        this.left = 0;
        this.right = array.length - 1;
    }

    private MergeSortAction(int[] array, int left, int right) {
        this.array = array;
        this.left = left;
        this.right = right;
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = (left + right) / 2;
            MergeSortAction leftTask = new MergeSortAction(array, left, mid);
            MergeSortAction rightTask = new MergeSortAction(array, mid + 1, right);
            invokeAll(leftTask, rightTask);
            marge(left, mid, right);
        }
    }

    private void marge(int left, int mid, int right) {
        int[] leftTempArray = Arrays.copyOfRange(array, left, mid + 1);
        int[] rightTempArray = Arrays.copyOfRange(array, mid + 1, right + 1);

        int leftIndex = 0;
        int rightIndex = 0;
        int arrayIndex = left;

        while (leftIndex < leftTempArray.length || rightIndex < rightTempArray.length) {
            if (leftIndex < leftTempArray.length && rightIndex < rightTempArray.length) {
                if (leftTempArray[leftIndex] <= rightTempArray[rightIndex]) {
                    array[arrayIndex] = leftTempArray[leftIndex++];
                } else {
                    array[arrayIndex] = rightTempArray[rightIndex++];
                }
            } else if (leftIndex < leftTempArray.length) {
                array[arrayIndex] = leftTempArray[leftIndex++];
            } else if (rightIndex < rightTempArray.length) {
                array[arrayIndex] = rightTempArray[rightIndex++];
            }
            arrayIndex++;
        }
    }
}
