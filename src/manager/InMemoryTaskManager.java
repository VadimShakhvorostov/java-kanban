package manager;

import history.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    private int id;
    protected final Map<Integer, Task> tasks = new HashMap<>();
    protected final Map<Integer, Subtask> subtasks = new HashMap<>();
    protected final Map<Integer, Epic> epics = new HashMap<>();

    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    private void updateId() {
        id++;
    }

    @Override
    public Collection<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    @Override
    public void createTask(Task task) {
        if (!tasks.containsValue(task)) {
            updateId();
            task.setId(this.id);
            task.setStatus(TaskStatus.NEW);
            tasks.put(this.id, task);
        }
    }

    @Override
    public void createSubtask(Subtask subtask, Epic epic) {
        if (!subtasks.containsValue(subtask)) {
            updateId();
            subtask.setEpic(epic);
            subtask.setStatus(TaskStatus.NEW);
            subtask.setId(this.id);
            epic.setSubtask(subtask);
            epic.setStatus(TaskStatus.NEW);
            subtasks.put(this.id, subtask);
        }
    }

    @Override
    public void createEpic(Epic epic) {
        if (!epics.containsValue(epic)) {
            updateId();
            epic.setId(this.id);
            epic.setStatus(TaskStatus.NEW);
            epics.put(id, epic);
        }
    }

    @Override
    public Collection<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public Collection<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public Collection<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllTask() {
        tasks.clear();
    }

    private void clearSubtaskInEpic() {
        for (Epic e : epics.values()) {
            e.removeEpicsSubtask();
            e.setStatus();
        }
    }

    @Override
    public void removeAllSubtask() {
        subtasks.clear();
        clearSubtaskInEpic();
    }

    @Override
    public void removeAllEpics() {
        clearSubtaskInEpic();
        removeAllSubtask();
        epics.clear();
    }

    @Override
    public Task getTasksById(int id) {
        inMemoryHistoryManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Subtask getSubTasksById(int id) {
        inMemoryHistoryManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public Epic getEpicsById(int id) {
        inMemoryHistoryManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void updateTask(Task newTask) {
        TaskStatus oldTaskStatus = tasks.get(newTask.getId()).getStatus();
        int oldIdTask = newTask.getId();
        newTask.setStatus(oldTaskStatus);
        tasks.replace(oldIdTask, newTask);
    }

    @Override
    public void updateSubTask(Subtask newSubtask) {
        TaskStatus oldSubtaskStatus = subtasks.get(newSubtask.getId()).getStatus();
        int oldIdSubtask = newSubtask.getId();
        Subtask oldSubtask = subtasks.get(oldIdSubtask);
        newSubtask.setEpic(oldSubtask.getEpic());
        newSubtask.setId(oldIdSubtask);
        newSubtask.setStatus(oldSubtaskStatus);
        newSubtask.getEpic().replaceSubtask(newSubtask);
        newSubtask.getEpic().setStatus();
        subtasks.replace(newSubtask.getId(), newSubtask);

    }

    @Override
    public void updateEpic(Epic newEpic) {
        TaskStatus oldEpicStatus = epics.get(newEpic.getId()).getStatus();
        int oldIdEpic = newEpic.getId();
        Epic oldEpic = getEpicsById(oldIdEpic);
        newEpic.setEpicsSubtask(oldEpic.getEpicsSubtask());
        newEpic.setStatus(oldEpicStatus);
        epics.replace(oldIdEpic, newEpic);
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        subtasks.get(id).getEpic().removeSubtask(id);
        subtasks.get(id).getEpic().setStatus();
        subtasks.remove(id);

    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        for (Subtask s : epic.getEpicsSubtask()) {
            subtasks.remove(s.getId());
        }
        epics.remove(id);
    }

    @Override
    public List<Subtask> getEpicsSubtasks(Epic epic) {
        return epic.getEpicsSubtask();
    }

    @Override
    public void changeStatusToProgress(Task task) {
        task.setStatus(TaskStatus.IN_PROGRESS);
        if (task instanceof Subtask) {
            ((Subtask) task).getEpic().setStatus();
        }
    }

    @Override
    public void changeStatusToDone(Task task) {
        task.setStatus(TaskStatus.DONE);
        if (task instanceof Subtask) {
            ((Subtask) task).getEpic().setStatus();
        }
    }
}
