package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;

    private int[] array;
    private int startPoint;
    private int endPoint;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    private MergeSortAction(int[] array, int startPoint, int endPoint) {
        this.array = array;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    protected void compute() {
        if (endPoint - startPoint <= THRESHOLD) {
            Arrays.sort(array, startPoint, endPoint);
        } else {
            int middlePoint = startPoint + (endPoint - startPoint) / 2;
            MergeSortAction leftTask = new MergeSortAction(array, startPoint, middlePoint);
            MergeSortAction rightTask = new MergeSortAction(array, middlePoint, endPoint);

            rightTask.fork();

            leftTask.compute();
            rightTask.join();

            merge(startPoint, middlePoint, endPoint);
        }
    }

    private void merge(int startPoint, int middlePoint, int endPoint) {
        int[] left = Arrays.copyOfRange(array, startPoint, middlePoint);
        int[] right = Arrays.copyOfRange(array, middlePoint, endPoint);

        int arrayIndex = startPoint;
        int leftIndex = 0;
        int rightIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                array[arrayIndex++] = left[leftIndex++];
            } else {
                array[arrayIndex++] = right[rightIndex++];
            }
        }

        while (leftIndex < left.length) {
            array[arrayIndex++] = left[leftIndex++];
        }

        while (rightIndex < right.length) {
            array[arrayIndex++] = right[rightIndex++];
        }
    }
}
