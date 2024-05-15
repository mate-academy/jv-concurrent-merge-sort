package mate.academy;

import java.util.List;
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
        int midIndex = array.length >> 1;
        int[] leftArray = createCopy(0, midIndex);
        int[] rightArray = createCopy(midIndex, array.length);
        List<MergeSortAction> subSortActions =
                List.of(new MergeSortAction(leftArray), new MergeSortAction(rightArray));
        subSortActions.forEach(MergeSortAction::fork);
        subSortActions.forEach(MergeSortAction::join);
        merge(leftArray, rightArray);
    }

    private void merge(int[] leftArray, int[] rightArray) {
        int i = 0;
        int j = 0;
        while (i < leftArray.length && j < rightArray.length) {
            array[i + j] = leftArray[i] <= rightArray[j] ? leftArray[i++] : rightArray[j++];
        }
        while (i < leftArray.length) {
            array[i + j] = leftArray[i++];
        }
        while (j < rightArray.length) {
            array[i + j] = rightArray[j++];
        }
    }

    private int[] createCopy(int startIndex, int endIndex) {
        int[] result = new int[endIndex - startIndex];
        System.arraycopy(array, startIndex, result, 0, endIndex - startIndex);
        return result;
    }
}
