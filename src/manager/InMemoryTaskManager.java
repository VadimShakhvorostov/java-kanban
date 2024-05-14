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
    protected Set<Task> prioritizedTasks = new TreeSet<>(Comparator.nullsLast(Comparator.comparing(Task::getStartTime,
            Comparator.nullsLast(Comparator.naturalOrder()))));

    HistoryManager inMemoryHistoryManager = Managers.getDefaultHistory();

    @Override
    public boolean validation(Task newTask) {
        return prioritizedTasks.stream()
                .allMatch(task ->
                        (((newTask.getStartTime().isBefore(task.getStartTime()))
                                && (newTask.getEndTime().isBefore(task.getStartTime())))
                                || ((newTask.getStartTime().isAfter(task.getEndTime()))
                                && (newTask.getEndTime().isAfter(task.getEndTime())))));
    }

    private void updateId() {
        id++;
    }

    @Override
    public Collection<Task> getHistory() {
        return inMemoryHistoryManager.getHistory();
    }

    private void addTaskWithoutTime(Task task) {
        task.setId(this.id);
        task.setStatus(TaskStatus.NEW);
    }

    private void addTaskWithTime(Task task) {
        addTaskWithoutTime(task);
        prioritizedTasks.add(task);
    }

    @Override
    public void createTask(Task task) {
        if (!tasks.containsValue(task)) {
            updateId();
            if (task.getStartTime() != null) {
                if (validation(task)) {
                    addTaskWithoutTime(task);
                    addTaskWithTime(task);
                }
            } else {
                addTaskWithoutTime(task);
            }
        }
        tasks.put(task.getId(), task);
    }

    private void addSubtaskWithoutTime(Subtask subtask, Epic epic) {
        subtask.setEpic(epic);
        subtask.setStatus(TaskStatus.NEW);
        subtask.setId(this.id);
        epic.setSubtask(subtask);
        epic.setStatus(TaskStatus.NEW);
    }

    private void addSubtaskWithTime(Subtask subtask, Epic epic) {
        addSubtaskWithoutTime(subtask, epic);
        epic.setStartTime();
        epic.setEndTime();
        epic.setDuration();
        prioritizedTasks.add(subtask);
    }

    @Override
    public void createSubtask(Subtask subtask, Epic epic) {
        if (!subtasks.containsValue(subtask)) {
            updateId();
            if (subtask.getStartTime() != null) {
                if (validation(subtask)) {
                    addSubtaskWithoutTime(subtask, epic);
                    addSubtaskWithTime(subtask, epic);
                }
            } else {
                addSubtaskWithoutTime(subtask, epic);
            }
        }
        subtasks.put(this.id, subtask);
    }

    @Override
    public Set<Task> getPrioritizedTasks() {
        return prioritizedTasks;
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
        for (Epic epic : epics.values()) {
            epic.removeEpicsSubtask();
            epic.setStatus();
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

    private void addTaskToUpdateWithoutTime(Task newTask, TaskStatus oldTaskStatus, int oldIdTask) {
        newTask.setStatus(oldTaskStatus);
        prioritizedTasks.remove(tasks.get(oldIdTask));
    }

    private void addTaskToUpdateWithTime(Task newTask, TaskStatus oldTaskStatus, int oldIdTask) {
        addTaskToUpdateWithoutTime(newTask, oldTaskStatus, oldIdTask);
        prioritizedTasks.remove(tasks.get(oldIdTask));
        prioritizedTasks.add(newTask);
    }

    @Override
    public void updateTask(Task newTask) {
        TaskStatus oldTaskStatus = tasks.get(newTask.getId()).getStatus();
        int oldIdTask = newTask.getId();
        if (newTask.getStartTime() == null) {
            addTaskToUpdateWithoutTime(newTask, oldTaskStatus, oldIdTask);
        } else {
            if (validation(newTask)) {
                addTaskToUpdateWithTime(newTask, oldTaskStatus, oldIdTask);
            }
        }
        tasks.replace(oldIdTask, newTask);
    }

    private void addSubtaskToUpdateWithTime(Subtask newSubtask, int oldIdSubtask, TaskStatus oldSubtaskStatus, Subtask oldSubtask) {
        addSubtaskToUpdateWithoutTime(newSubtask, oldIdSubtask, oldSubtaskStatus, oldSubtask);
        oldSubtask.getEpic().setStartTime();
        oldSubtask.getEpic().setEndTime();
        oldSubtask.getEpic().setDuration();

    }

    private void addSubtaskToUpdateWithoutTime(Subtask newSubtask, int oldIdSubtask, TaskStatus oldSubtaskStatus, Subtask oldSubtask) {
        newSubtask.setEpic(oldSubtask.getEpic());
        newSubtask.setId(oldIdSubtask);
        newSubtask.setStatus(oldSubtaskStatus);
        newSubtask.getEpic().replaceSubtask(newSubtask);
        newSubtask.getEpic().setStatus();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        int oldIdSubtask = newSubtask.getId();
        TaskStatus oldSubtaskStatus = subtasks.get(newSubtask.getId()).getStatus();
        Subtask oldSubtask = subtasks.get(oldIdSubtask);
        if (newSubtask.getStartTime() == null) {
            addSubtaskToUpdateWithoutTime(newSubtask, oldIdSubtask, oldSubtaskStatus, oldSubtask);
            prioritizedTasks.remove(tasks.get(oldIdSubtask));
        } else {
            addSubtaskToUpdateWithTime(newSubtask, oldIdSubtask, oldSubtaskStatus, oldSubtask);
            prioritizedTasks.remove(tasks.get(oldIdSubtask));
            prioritizedTasks.add(newSubtask);
        }
        subtasks.replace(newSubtask.getId(), newSubtask);
    }

    @Override
    public void updateEpic(Epic newEpic) {
        TaskStatus oldEpicStatus = epics.get(newEpic.getId()).getStatus();
        int oldIdEpic = newEpic.getId();
        Epic oldEpic = getEpicsById(oldIdEpic);
        newEpic.setEpicsSubtask(oldEpic.getEpicsSubtask());
        newEpic.setStatus(oldEpicStatus);
        newEpic.setStartTime();
        newEpic.setEndTime();
        newEpic.setDuration();
        epics.replace(oldIdEpic, newEpic);
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeSubtaskById(int id) {
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
