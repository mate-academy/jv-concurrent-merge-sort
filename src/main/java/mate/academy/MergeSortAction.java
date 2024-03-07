package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;
    private final int left;
    private final int right;

    public MergeSortAction(int[] array) {
        this(array,0,array.length - 1);
    }

    private MergeSortAction(int[] array, int l, int r) {
        this.array = array;
        this.left = l;
        this.right = r;
    }

    public void merge(int[] array, int left, int middle, int right) {
        int[] leftArray = new int[middle - left + 1];
        int[] rightArray = new int[right - middle];

        System.arraycopy(array, left, leftArray, 0, leftArray.length);
        System.arraycopy(array, middle + 1, rightArray, 0, rightArray.length);

        int i = 0;
        int j = 0;
        int k = left;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                array[k++] = rightArray[j++];
            }
        }

        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }

        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }
    }

    @Override
    protected void compute() {
        if (left < right) {
            int mid = left + (right - left) / 2;
            MergeSortAction leftMergeSortAction = new MergeSortAction(array, left, mid);
            MergeSortAction rightMergeSortAction = new MergeSortAction(array, mid + 1, right);
            leftMergeSortAction.fork();
            rightMergeSortAction.fork();
            leftMergeSortAction.join();
            rightMergeSortAction.join();
            merge(array,left,mid,right);
        }
    }
}
