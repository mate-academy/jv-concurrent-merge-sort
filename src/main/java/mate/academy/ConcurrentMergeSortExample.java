package mate.academy;

import java.util.concurrent.ForkJoinPool;

public class ConcurrentMergeSortExample {

    public static void sort(int[] inputArray) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new MergeSortAction(inputArray));
    }

    public static void main(String[] args) {
        int[] inputArray = {12, 11, 0, 13, 5, 6, 7, -1};

        System.out.println("Original array: ");
        for (int value : inputArray) {
            System.out.print(value + " ");
        }

        sort(inputArray);

        System.out.println("\nSorted array: ");
        for (int value : inputArray) {
            System.out.print(value + " ");
        }
    }
}
