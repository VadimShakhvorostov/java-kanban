package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.util.*;

public class Manager {

    private int id;
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Map<Integer, Subtask> subtasks = new HashMap<>();
    private final Map<Integer, Epic> epics = new HashMap<>();

    private void updateId() {
        id++;
    }

    public void createTask(Task task) {
        updateId();
        task.setId(this.id);
        task.setStatus(TaskStatus.NEW);
        tasks.put(this.id, task);
    }

    public void createSubtask(Subtask subtask, Epic epic) {
        updateId();
        subtask.setEpic(epic);
        subtask.setStatus(TaskStatus.NEW);
        subtask.setId(this.id);
        epic.addSubtask(subtask);
        epic.setStatus(TaskStatus.NEW);
        subtasks.put(this.id, subtask);
    }

    public void createEpic(Epic epic) {
        updateId();
        epic.setId(this.id);
        epic.setStatus(TaskStatus.NEW);
        epics.put(id, epic);
    }

    public Collection<Task> getTasks() {
        return new ArrayList<>(tasks.values()) ;
    }

    public Collection<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public Collection<Epic> getEpics() {
        return new ArrayList<>(epics.values()) ;
    }

    public void removeAllTask() {
        tasks.clear();
    }

    private void clearSubtaskInEpic() {
        for (Epic e : epics.values()) {
            e.removeEpicsSubtask();
            e.setStatus();
        }
    }

    public void removeAllSubtask() {
        subtasks.clear();
        clearSubtaskInEpic();
    }

    public void removeAllEpics() {
        clearSubtaskInEpic();
        removeAllSubtask();
        epics.clear();

    }

    public Task getTasksById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubTasksById(int id) {
        return subtasks.get(id);
    }

    public Epic getEpicsById(int id) {
        return epics.get(id);
    }

    public void updateTask(Task newTask) {
        TaskStatus oldTaskStatus = newTask.getStatus();
        int oldIdTask = newTask.getId();

        newTask.setStatus(oldTaskStatus);
        tasks.replace(oldIdTask, newTask);
    }

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

    public void updateEpic(Epic newEpic) {
        TaskStatus oldEpicStatus = epics.get(newEpic.getId()).getStatus();
        int oldIdEpic = newEpic.getId();
        Epic oldEpic = getEpicsById(oldIdEpic);
        newEpic.setEpicsSubtask(oldEpic.getEpicsSubtask());
        newEpic.setStatus(oldEpicStatus);
        epics.replace(oldIdEpic, newEpic);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeSubTaskById(int id) {
        subtasks.get(id).getEpic().removeSubtask(id);
        subtasks.get(id).getEpic().setStatus();
        subtasks.remove(id);

    }

    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        for (Subtask s : epic.getEpicsSubtask()) {
            subtasks.remove(s.getId());
        }
        epics.remove(id);
    }


    public List<Subtask> getEpicsSubtasks(Epic epic) {
        return epic.getEpicsSubtask();
    }

    public void changeStatusToProgress(Task task) {
        task.setStatus(TaskStatus.IN_PROGRESS);
        if (task instanceof Subtask) {
            ((Subtask) task).getEpic().setStatus();
        }
    }

    public void changeStatusToDone(Task task) {
        task.setStatus(TaskStatus.DONE);
        if (task instanceof Subtask) {
            ((Subtask) task).getEpic().setStatus();
        }
    }
}
