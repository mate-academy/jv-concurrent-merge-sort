package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction extends RecursiveTask<int[]> {
    private static final int MAX_ARRAY_SIZE = 2;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected int[] compute() {
        if (array.length < MAX_ARRAY_SIZE) {
            return array;

        } else {
            int middle = array.length / 2;
            int[] firstArray = Arrays.copyOfRange(array, 0, middle);
            int[] secondArray = Arrays.copyOfRange(array, middle, array.length);

            MergeSortAction first = new MergeSortAction(firstArray);
            MergeSortAction second = new MergeSortAction(secondArray);

            first.fork();
            second.fork();
            int[] firstSortedArray = first.join();
            int[] secondSortedArray = second.join();
            int[] sorted = sortSortedArrays(firstSortedArray, secondSortedArray);

            return sorted;
        }
    }

    private int[] sortSortedArrays(int[] firstArray, int[] secondArray) {
        int[] result = new int[firstArray.length + secondArray.length];
        int indexOfFirstArray = 0;
        int indexOfSecondArray = 0;
        int index = 0;

        while (indexOfFirstArray < firstArray.length && indexOfSecondArray < secondArray.length) {
            if (firstArray[indexOfFirstArray]
                    < secondArray[indexOfSecondArray]) {
                result[index] = firstArray[indexOfFirstArray];
                indexOfFirstArray++;
                index++;
            } else {
                result[index] = secondArray[indexOfSecondArray];
                indexOfSecondArray++;
                index++;
            }
        }

        while (indexOfFirstArray < firstArray.length) {
            result[index] = firstArray[indexOfFirstArray];
            indexOfFirstArray++;
            index++;
        }

        while (indexOfSecondArray < secondArray.length) {
            result[index] = secondArray[indexOfSecondArray];
            indexOfSecondArray++;
            index++;
        }

        return result;
    }
}
