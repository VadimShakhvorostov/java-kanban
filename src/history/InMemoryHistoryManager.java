package history;

import tasks.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final LinkedList<Task> history = new LinkedList<>();
    private static int HISTORY_SIZE = 10;

    private void checkHistory() {
        if (history.size() >= HISTORY_SIZE) {
            history.removeFirst();
        }
    }

    @Override
    public void add(Task task) {
        if (task == null)
            return;
        history.add(task);
        checkHistory();
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
