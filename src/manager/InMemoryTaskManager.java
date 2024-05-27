package manager;

import exception.ValidationException;
import history.HistoryManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
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
        Set<Task> prioritizedTasksWithoutUpdateTask = new HashSet<>(Set.copyOf(prioritizedTasks));
        prioritizedTasksWithoutUpdateTask.remove(newTask);
        return prioritizedTasksWithoutUpdateTask.stream()
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
    public void createTask(Task task) throws FileNotFoundException {
        if (!tasks.containsValue(task)) {
            updateId();
            if (task.getStartTime() != null) {
                if (validation(task)) {
                    addTaskWithoutTime(task);
                    addTaskWithTime(task);
                } else {
                    throw new ValidationException("Пересекаются по времени");
                }
            } else {
                addTaskWithoutTime(task);
            }
            tasks.put(task.getId(), task);
        } else {
            throw new FileNotFoundException("Нет таска который можно заменить");
        }
    }


    private void addSubtaskWithoutTime(Subtask subtask, Epic epic) {
        subtask.setId(this.id);
        subtask.setEpicId(epic.getId());
        subtask.setStatus(TaskStatus.NEW);
        epic.setSubtask(subtask.getId());
        epic.setStatus(TaskStatus.NEW);
    }

    private void addSubtaskWithTime(Subtask subtask, Epic epic) {
        addSubtaskWithoutTime(subtask, epic);

        prioritizedTasks.add(subtask);
    }

    @Override
    public void createSubtask(Subtask subtask) {


        if (epics.containsKey(subtask.getEpicId())) {
            Epic epic = epics.get(subtask.getEpicId());


            if (!subtasks.containsValue(subtask)) {
                updateId();
                subtask.setId(this.id);
                if (subtask.getStartTime() != null) {
                    if (validation(subtask)) {
                        addSubtaskWithTime(subtask, epic);
                    } else {
                        throw new ValidationException("Пересекаются по времени");
                    }
                } else {
                    addSubtaskWithoutTime(subtask, epic);
                }

                subtasks.put(this.id, subtask);
                setStartTimeAndDurationEpic(epic);
            }

        }
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
            setStatusEpic(epic);
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

    }

    private void addTaskToUpdateWithTime(Task newTask, TaskStatus oldTaskStatus, int oldIdTask) {
        addTaskToUpdateWithoutTime(newTask, oldTaskStatus, oldIdTask);

        prioritizedTasks.add(newTask);
    }

    @Override
    public void updateTask(Task newTask) throws FileNotFoundException {
        if (tasks.containsKey(newTask.getId())) {
            TaskStatus oldTaskStatus = tasks.get(newTask.getId()).getStatus();
            int oldIdTask = newTask.getId();


            if (newTask.getStartTime() == null) {
                addTaskToUpdateWithoutTime(newTask, oldTaskStatus, oldIdTask);
            } else {
                prioritizedTasks.remove(tasks.get(oldIdTask));
                if (validation(newTask)) {
                    addTaskToUpdateWithTime(newTask, oldTaskStatus, oldIdTask);
                } else {
                    throw new ValidationException("Пересекаются по времени");
                }
            }
            tasks.replace(oldIdTask, newTask);
        } else {
            throw new FileNotFoundException("Нет таска который можно заменить");
        }
    }

    private void addSubtaskToUpdateWithTime(Subtask newSubtask, int oldIdSubtask, TaskStatus oldSubtaskStatus, Subtask oldSubtask) {
        addSubtaskToUpdateWithoutTime(newSubtask, oldIdSubtask, oldSubtaskStatus, oldSubtask);
        prioritizedTasks.add(newSubtask);

    }

    private void addSubtaskToUpdateWithoutTime(Subtask newSubtask, int oldIdSubtask, TaskStatus oldSubtaskStatus, Subtask oldSubtask) {
        newSubtask.setEpicId(oldSubtask.getEpicId());
        newSubtask.setId(oldIdSubtask);
        newSubtask.setStatus(oldSubtaskStatus);
        setStatusEpic(epics.get(newSubtask.getEpicId()));
        epics.get(newSubtask.getEpicId());
    }

    @Override
    public void updateSubtask(Subtask newSubtask) throws FileNotFoundException {
        if (subtasks.containsKey(newSubtask.getId())) {
            int oldIdSubtask = newSubtask.getId();
            TaskStatus oldSubtaskStatus = subtasks.get(newSubtask.getId()).getStatus();
            Subtask oldSubtask = subtasks.get(oldIdSubtask);
            if (newSubtask.getStartTime() == null) {
                addSubtaskToUpdateWithoutTime(newSubtask, oldIdSubtask, oldSubtaskStatus, oldSubtask);
            } else {
                prioritizedTasks.remove(subtasks.get(oldIdSubtask));
                if (validation(newSubtask)) {
                    addSubtaskToUpdateWithTime(newSubtask, oldIdSubtask, oldSubtaskStatus, oldSubtask);
                } else {
                    throw new ValidationException("Пересекаются по времени");
                }
            }
            subtasks.replace(newSubtask.getId(), newSubtask);
            setStartTimeAndDurationEpic(epics.get(oldSubtask.getEpicId()));
        } else {
            throw new FileNotFoundException("Нет таска который можно заменить");
        }
    }

    @Override
    public void updateEpic(Epic newEpic) {
        TaskStatus oldEpicStatus = epics.get(newEpic.getId()).getStatus();
        int oldIdEpic = newEpic.getId();
        Epic oldEpic = getEpicsById(oldIdEpic);
        newEpic.setSubtaskId(oldEpic.getSubtaskId());
        newEpic.setStatus(oldEpicStatus);
        setStartTimeAndDurationEpic(newEpic);
        epics.replace(oldIdEpic, newEpic);
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);

    }

    @Override
    public void removeSubtaskById(int id) {
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getEpicId());
            epic.removeSubtask(id);
            setStatusEpic(epic);
            setStartTimeAndDurationEpic(epic);
            subtasks.remove(id);
        }
    }

    @Override
    public void removeEpicById(int id) {
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            for (Integer subtaskId : epic.getSubtaskId()) {
                subtasks.remove(subtaskId);
            }
            epics.remove(id);
        }
    }

    @Override
    public List<Subtask> getEpicsSubtasks(Epic epic) {

        ArrayList<Subtask> epicsSubtask = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtaskId()) {
            if (subtasks.containsKey(subtaskId)) {
                epicsSubtask.add(subtasks.get(subtaskId));
            }
        }
        return epicsSubtask;
    }

    @Override
    public void changeStatusToProgress(Task task) {
        task.setStatus(TaskStatus.IN_PROGRESS);
        if (task instanceof Subtask) {
            setStatusEpic(epics.get(((Subtask) task).getEpicId()));
        }
    }

    @Override
    public void changeStatusToDone(Task task) {
        task.setStatus(TaskStatus.DONE);
        if (task instanceof Subtask) {
            setStatusEpic(epics.get(((Subtask) task).getEpicId()));
        }
    }

    public void setStatusEpic(Epic epic) {
        if (epic.getSubtaskId().isEmpty()) {
            epic.setStatus(TaskStatus.NEW);
        }
        int countDone = 0;
        int countNew = 0;

        for (Integer subtaskId : subtasks.keySet()) {
            if (epic.getSubtaskId().contains(subtaskId)) {
                if (subtasks.get(subtaskId).getStatus() == TaskStatus.DONE) {
                    countDone++;
                }
                if (subtasks.get(subtaskId).getStatus() == TaskStatus.NEW) {
                    countNew++;
                }
            }
        }
        if (epic.getSubtaskId().size() == countDone) {
            epic.setStatus(TaskStatus.DONE);
        } else if (epic.getSubtaskId().size() == countNew) {
            epic.setStatus(TaskStatus.NEW);
        } else {
            epic.setStatus(TaskStatus.IN_PROGRESS);
        }
    }

    protected void setStartTimeAndDurationEpic(Epic epic) {

        ArrayList<Subtask> epicsSubtask = new ArrayList<>();
        for (Integer subtaskId : epic.getSubtaskId()) {
            if (subtasks.containsKey(subtaskId)) {
                epicsSubtask.add(subtasks.get(subtaskId));
            }
        }

        LocalDateTime startTime = epicsSubtask.stream()
                .filter(subtask -> subtask.getStartTime() != null)
                .min(Comparator.comparing(Subtask::getStartTime))
                .map(subtask -> subtask.getStartTime())
                .orElse(null);

        LocalDateTime endTime = epicsSubtask.stream()
                .filter(subtask -> subtask.getEndTime() != null)
                .max(Comparator.comparing(Subtask::getEndTime))
                .map(subtask -> subtask.getEndTime())
                .orElse(null);

        epic.setStartTime(startTime);
        epic.setEndTime(endTime);
        epic.setDuration();
    }
}