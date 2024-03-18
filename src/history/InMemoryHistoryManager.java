package history;

import tasks.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private final List<Task> history = new ArrayList<>();

    private void checkHistory(){
        while(history.size() > 10) {
            history.remove(0);
        }
    }

    @Override
    public void add(Task task) {
        history.add(task);
        checkHistory();
    }

    @Override
    public Collection<Task> getHistory() {
        return history;
    }
}
