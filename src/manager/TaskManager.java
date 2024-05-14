package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface TaskManager {

    Collection<Task> getHistory();

    void createTask(Task task);

    void createSubtask(Subtask subtask, Epic epic);

    void createEpic(Epic epic);

    Collection<Task> getTasks();

    Collection<Subtask> getSubtasks();

    Collection<Epic> getEpics();

    void removeAllTask();

    void removeAllSubtask();

    void removeAllEpics();

    Task getTasksById(int id);

    Subtask getSubTasksById(int id);

    Epic getEpicsById(int id);

    void updateTask(Task newTask);

    void updateSubtask(Subtask newSubtask);

    void updateEpic(Epic newEpic);

    void removeTaskById(int id);

    void removeSubtaskById(int id);

    void removeEpicById(int id);

    List<Subtask> getEpicsSubtasks(Epic epic);

    void changeStatusToProgress(Task task);

    void changeStatusToDone(Task task);

    Set<Task> getPrioritizedTasks();

    boolean validation(Task task);
}
