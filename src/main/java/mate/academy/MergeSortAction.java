package mate.academy;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] nums;
    private final int start;
    private final int end;

    public MergeSortAction(int[] array) {
        this(array, 0, array.length - 1);
    }

    private MergeSortAction(int[] array, int start, int end) {
        nums = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected void compute() {
        if ((end - start + 1) <= 1) {
            return;
        }

        int mid = (start + end) / 2;

        MergeSortAction leftFork = new MergeSortAction(nums, start, mid);
        MergeSortAction rightFork = new MergeSortAction(nums, mid + 1, end);

        leftFork.fork();
        rightFork.fork();
        leftFork.join();
        rightFork.join();

        MergeAction mergeFork = new MergeAction(nums, start, mid, end);
        mergeFork.fork();
        mergeFork.join();
    }

    private class MergeAction extends RecursiveAction {
        private final int[] nums;
        private final int start;
        private final int mid;
        private final int end;

        public MergeAction(int[] nums, int start, int mid, int end) {
            this.nums = nums;
            this.start = start;
            this.mid = mid;
            this.end = end;
        }

        @Override
        protected void compute() {
            int[] left = new int[mid - start + 1];
            int[] right = new int[end - mid];

            System.arraycopy(nums, start, left, 0, left.length);
            System.arraycopy(nums, mid + 1, right, 0, right.length);

            int i = 0;
            int j = 0;
            int k = start;

            while (i < left.length && j < right.length) {
                if (left[i] < right[j]) {
                    nums[k] = left[i];
                    i++;
                } else {
                    nums[k] = right[j];
                    j++;
                }
                k++;
            }

            while (i < left.length) {
                nums[k] = left[i];
                k++;
                i++;
            }

            while (j < right.length) {
                nums[k] = right[j];
                k++;
                j++;
            }
        }
    }
}
