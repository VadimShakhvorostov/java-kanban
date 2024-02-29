import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    private int id;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;

    public Manager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public void createTask(Task task) {
        id++;
        task.setId(this.id);
        task.setStatus(TaskStatus.NEW);
        tasks.put(this.id, task);
    }

    public void createSubtask(Subtask subtask, Epic epic) {
        id++;
        subtask.setSubtasksEpics(epic);
        subtask.setStatus(TaskStatus.NEW);
        subtask.setId(this.id);
        epic.addSubtask(subtask);
        epic.setStatus(TaskStatus.NEW);
        subtasks.put(this.id, subtask);
    }

    public void createEpic(Epic epic) {
        id++;
        epic.setId(this.id);
        epic.setStatus(TaskStatus.NEW);
        epics.put(id, epic);
    }

    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public void removeAllTask() {
        tasks.clear();
    }

    public void removeAllSubtask() {
        subtasks.clear();
    }

    public void removeAllEpics() {
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

    public void updateTask(Task oldTask, Task newTask) {
        newTask.setStatus(TaskStatus.NEW);
        tasks.replace(oldTask.getId(), newTask);
    }

    public void updateSubTask(Subtask oldSubtask, Subtask newSubtask) {
        newSubtask.setStatus(TaskStatus.NEW);
        tasks.replace(oldSubtask.getId(), newSubtask);
    }

    public void updateEpic(Epic oldEpic, Epic newEpic) {
        newEpic.setStatus(TaskStatus.NEW);
        tasks.replace(oldEpic.getId(), newEpic);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeSubTaskById(int id) {
        subtasks.remove(id);
    }

    public void removeEpicById(int id) {
        epics.remove(id);
    }

    public ArrayList<Subtask> getEpicsSubtasks(Epic epic) {
        return epic.getEpicsSubtask();
    }

    public void changeStatusToProgress(Task task) {
        task.setStatus(TaskStatus.IN_PROGRESS);
        if (task instanceof Subtask) {
            ((Subtask) task).getSubtasksEpics().setStatus();
        }
    }

    public void changeStatusToDone(Task task) {
        task.setStatus(TaskStatus.DONE);
        if (task instanceof Subtask) {
            ((Subtask) task).getSubtasksEpics().setStatus();
        }
    }
}
