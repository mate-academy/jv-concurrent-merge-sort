package mate.academy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {
    private final int[] array;

    public MergeSortAction(int[] array) {
        this.array = array;
    }

    @Override
    protected void compute() {
        if (array.length == 0) {
            List<RecursiveAction> actions = new ArrayList<>(createSubTasks());
            for (RecursiveAction action : actions) {
                action.fork();
            }
        } else {
            Arrays.sort(array);
        }
    }

    private List<RecursiveAction> createSubTasks() {
        List<RecursiveAction> subTasks = new ArrayList<>();
        MergeSortAction first = new MergeSortAction(
                Arrays.copyOfRange(array, 0, array.length / 2));
        MergeSortAction second = new MergeSortAction(
                Arrays.copyOfRange(array, array.length / 2, array.length));

        subTasks.add(first);
        subTasks.add(second);

        return subTasks;
    }
}
