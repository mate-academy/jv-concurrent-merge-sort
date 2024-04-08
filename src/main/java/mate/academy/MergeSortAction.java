package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 1;
    private int start;
    private int end;
    private int[] array;

    public MergeSortAction(int[] array) {
        this(0, array.length, array);
    }

    private MergeSortAction(int start, int end, int[] array) {
        this.start = start;
        this.end = end;
        this.array = array;
    }

    @Override
    protected void compute() {
        if ((end - start) > THRESHOLD) {
            int middle = start + (end - start) / 2;
            MergeSortAction leftSubTask = new MergeSortAction(start, middle, array);
            MergeSortAction rightSubTask = new MergeSortAction(middle, end, array);
            leftSubTask.fork();
            rightSubTask.fork();
            leftSubTask.join();
            rightSubTask.join();
            sortAndMerge(start, end);
        }
    }

    private void sortAndMerge(int start, int end) {
        int middle = start + (end - start) / 2;
        int[] leftArray = new int[middle - start];
        int[] rightArray = new int[end - middle];
        System.arraycopy(array, start, leftArray, 0, leftArray.length);
        System.arraycopy(array, middle, rightArray, 0, rightArray.length);
        int leftIndex = 0;
        int rightIndex = 0;
        int mainArrayIndex = start;

        while (leftIndex < leftArray.length && rightIndex < rightArray.length) {
            if (leftArray[leftIndex] <= rightArray[rightIndex]) {
                array[mainArrayIndex] = leftArray[leftIndex++];
            } else {
                array[mainArrayIndex] = rightArray[rightIndex++];
            }
            mainArrayIndex++;
        }
        while (leftIndex < leftArray.length) {
            array[mainArrayIndex++] = leftArray[leftIndex++];
        }
        while (rightIndex < rightArray.length) {
            array[mainArrayIndex++] = rightArray[rightIndex++];
        }
    }
}
