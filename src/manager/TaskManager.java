package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

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

    void updateSubTask(Subtask newSubtask);

    void updateEpic(Epic newEpic);

    void removeTaskById(int id);

    void removeSubTaskById(int id);

    void removeEpicById(int id);

    List<Subtask> getEpicsSubtasks(Epic epic);

    void changeStatusToProgress(Task task);

    void changeStatusToDone(Task task);
}
