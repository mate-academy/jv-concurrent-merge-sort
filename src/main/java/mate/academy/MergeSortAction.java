package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] workLoad;

    public MergeSortAction(int[] array) {
        this.workLoad = array;
    }

    @Override
    protected void compute() {
        if (workLoad.length <= 1) {
            return;
        } else {
            int middle = workLoad.length / 2;
            MergeSortAction first = new MergeSortAction(divideArray(0, middle));
            MergeSortAction second = new MergeSortAction(divideArray(middle, workLoad.length));
            invokeAll(first, second);

            sortArrays(first.workLoad, second.workLoad);
        }
    }

    private void sortArrays(int[] first, int[] second) {
        int[] newArray = new int[first.length + second.length];
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < first.length && j < second.length) {
            if (first[i] <= second[j]) {
                newArray[k++] = first[i++];
            } else {
                newArray[k++] = second[j++];
            }
        }

        while (i < first.length) {
            newArray[k++] = first[i++];
        }

        while (j < second.length) {
            newArray[k++] = second[j++];
        }

        System.arraycopy(newArray, 0, workLoad, 0, newArray.length);
    }

    private int[] divideArray(int start, int end) {
        int[] newArray = new int[end - start];
        System.arraycopy(workLoad, start, newArray, 0, end - start);
        return newArray;
    }
}
