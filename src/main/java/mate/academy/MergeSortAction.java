package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 4;
    private final int[] array;
    private final int startPoint;
    private final int endPoint;

    public MergeSortAction(int[] array) {
        this.array = array;
        this.startPoint = 0;
        this.endPoint = array.length - 1;
    }

    private MergeSortAction(int[] array, int startPoint, int endPoint) {
        this.array = array;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    protected void compute() {
        if (endPoint - startPoint <= THRESHOLD) {
            sortArray(array, startPoint, endPoint);
        } else {
            int middle = startPoint + (endPoint - startPoint) / 2;
            MergeSortAction rightAction = new MergeSortAction(array, middle + 1, endPoint);
            rightAction.fork();
            MergeSortAction leftAction = new MergeSortAction(array, startPoint, middle);
            leftAction.compute();
            rightAction.join();
        }
    }

    private void sortArray(int[] array, int startPoint, int endPoint) {
        int key;
        int k;
        for (int i = startPoint; i <= endPoint; i++) {
            key = array[i];
            k = i - 1;
            while ((k >= 0) && (array[k] > key)) {
                array[k + 1] = array[k];
                k = k - 1;
            }
            array[k + 1] = key;
        }
    }
}
