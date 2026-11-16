package mate.academy;

import java.util.Arrays;
import java.util.concurrent.RecursiveTask;

public class MergeSortAction extends RecursiveTask<Integer[]> {

    public static final int THRESHOLD = 4;
    private final int startElement;
    private final int endElement;
    private final int[] array;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length);
    }

    public MergeSortAction(int[] array, int startElement, int endElement) {
        this.array = array;
        this.startElement = startElement;
        this.endElement = endElement;
    }

    @Override
    protected Integer[] compute() {
        if (endElement - startElement <= THRESHOLD) {
            return sortElement(array, startElement, endElement);
        }
        int middle = startElement + (endElement - startElement) / 2;
        MergeSortAction left = new MergeSortAction(array, startElement, middle);
        MergeSortAction right = new MergeSortAction(array, middle, endElement);
        left.fork();
        right.fork();
        Integer[] leftResult = left.join();
        Integer[] rightResult = right.join();
        Integer[] result = merge(leftResult, rightResult);
        for (int i = 0; i < result.length; i++) {
            array[startElement + i] = result[i];
        }
        return result;
    }

    private Integer[] sortElement(int[] array, int startElement, int endElement) {
        Arrays.sort(array, startElement, endElement);
        return Arrays.stream(Arrays.copyOfRange(array, startElement, endElement))
                .boxed()
                .toArray(Integer[]::new);
    }

    private Integer[] merge(Integer[] left, Integer[] right) {
        Integer[] merged = new Integer[left.length + right.length];
        int leftIndex = 0;
        int rightIndex = 0;
        int mergedIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] <= right[rightIndex]) {
                merged[mergedIndex++] = left[leftIndex++];
            } else {
                merged[mergedIndex++] = right[rightIndex++];
            }
        }
        while (leftIndex < left.length) {
            merged[mergedIndex++] = left[leftIndex++];
        }
        while (rightIndex < right.length) {
            merged[mergedIndex++] = right[rightIndex++];
        }
        return merged;
    }
}
