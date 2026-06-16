package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length < 4) {
            Arrays.sort(array);
            return;
        }

        int mid = array.length / 2;

        int[] left = Arrays.copyOfRange(array, 0, mid);
        int[] right = Arrays.copyOfRange(array, mid, array.length);

        MergeSortAction leftAction = new MergeSortAction(left);
        MergeSortAction rightAction = new MergeSortAction(right);

        invokeAll(leftAction, rightAction);

        merge(left, right, array);
    }

    private void merge(int[] left, int[] right, int[] result) {
        int[] merged = new int[array.length];
        int i = 0;

        for (int leftelement : left) {
            merged[i++] = leftelement;
        }

        for (int element : right) {
            merged[i++] = element;
        }

        Arrays.sort(merged);
        System.arraycopy(merged, 0, result, 0, merged.length);
    }
}
