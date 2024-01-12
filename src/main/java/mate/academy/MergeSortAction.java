package mate.academy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private static final int THRESHOLD = 2;

    private int[] array;

    private int[] left;

    private int[] right;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length == THRESHOLD) {
            final int min = Math.min(array[0], array[1]);
            if (array[0] != min) {
                array[1] = array[0];
                array[0] = min;
            }
        } else if (array.length > THRESHOLD) {
            List<RecursiveAction> subTasks = createSubTasks();
            for (RecursiveAction recursiveAction : subTasks) {
                recursiveAction.fork();
            }
            for (RecursiveAction recursiveAction : subTasks) {
                recursiveAction.join();
            }
            joinAndSortTwoArrays();
        }
    }

    private List<RecursiveAction> createSubTasks() {
        final int middleIndex = (int)Math.ceil(array.length / 2.0);
        left = Arrays.copyOf(array, middleIndex);
        right = Arrays.copyOfRange(array, middleIndex, array.length);

        List<RecursiveAction> tasks = new ArrayList<>();
        tasks.add(new MergeSortAction(left));
        tasks.add(new MergeSortAction(right));
        return tasks;
    }

    private void joinAndSortTwoArrays() {
        int rightDigitsUsedUp = 0;

        for (int i = 0; i < left.length; i++) {
            for (int j = rightDigitsUsedUp; j < right.length; j++) {
                if (left[i] <= right[j]) {
                    array[i + j] = left[i];
                    break;
                } else {
                    array[i + j] = right[j];
                    rightDigitsUsedUp++;
                }
            }
            
            if (rightDigitsUsedUp == right.length) {
                array[i + rightDigitsUsedUp] = left[i];
                continue;
            }
        }

        if (rightDigitsUsedUp < right.length) {
            for (int i = rightDigitsUsedUp; i < right.length; i++) {
                array[left.length + i] = right[i];
            }
        }
    }
}
