package manager;

import tasks.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private final File file;
    private static final String HEADER = "id,type,name,status,description,epic,startTime,endTime,duration, \n";
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");


    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            writer.write(HEADER);
            if (!getTasks().isEmpty()) {
                for (Task task : getTasks()) {
                    writer.write(task.toString());
                }
            }
            if (!getEpics().isEmpty()) {
                for (Epic epic : getEpics()) {
                    writer.write(epic.toString());
                }
            }
            if (!getSubtasks().isEmpty()) {
                for (Subtask subtask : getSubtasks()) {
                    writer.write(subtask.toString());
                }
            }
        } catch (IOException | ManagerSaveException e) {
            throw new ManagerSaveException("Can't write form file");
        }
    }

    public void loadFromFile(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (bufferedReader.readLine().isEmpty()) {
                return;
            }
            while (bufferedReader.ready()) {
                String[] string = bufferedReader.readLine().split(",");
                int length = string.length;
                final int id = Integer.parseInt(string[0]);
                final String name = string[2];
                final String description = string[4];
                final TaskStatus status = TaskStatus.valueOf(string[3]);
                final TaskType type = TaskType.valueOf(string[1]);


                switch (type.toString()) {
                    case "TASK":
                        Task task = new Task(name, description, id);
                        task.setStatus(status);
                        if (length > 5) {
                            LocalDateTime startTime = LocalDateTime.parse(string[6], DATE_TIME_FORMATTER);
                            int duration = Integer.parseInt(string[7]);
                            task.setStartTime(startTime);
                            task.setDuration(duration);
                        }
                        tasks.put(task.getId(), task);
                        break;
                    case "EPIC":
                        Epic epic = new Epic(name, description, id);
                        epic.setStatus();
                        epics.put(epic.getId(), epic);
                        break;
                    case "SUBTASK":
                        Subtask subtask = new Subtask(name, description, id);
                        subtask.setStatus(status);
                        if (length > 6) {
                            LocalDateTime startTime = LocalDateTime.parse(string[5], DATE_TIME_FORMATTER);
                            int duration = Integer.parseInt(string[7]);
                            subtask.setStartTime(startTime);
                            subtask.setDuration(duration);
                        }
                        subtasks.put(subtask.getId(), subtask);
                        break;
                }
            }
            setSubtaskToEpicAndTime();
        } catch (IOException e) {
            throw new ManagerSaveException("Can't read form file");
        }
    }

    private void setSubtaskToEpicAndTime() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (bufferedReader.readLine().isEmpty()) {
                return;
            }
            while (bufferedReader.ready()) {
                String[] string = bufferedReader.readLine().split(",");
                int length = string.length;
                if (string[1].equals("SUBTASK")) {
                    final int idSubtask = Integer.parseInt(string[0]);
                    final int epicSubtasks;
                    if (length > 6) {
                        epicSubtasks = Integer.parseInt(string[8]);
                    } else {
                        epicSubtasks = Integer.parseInt(string[5]);
                    }
                    Subtask subtask = subtasks.get(idSubtask);
                    Epic epic = epics.get(epicSubtasks);
                    subtask.setEpic(epic);
                    epic.setSubtask(subtask);
                    epic.setStatus();
                    epic.setStartTime();
                    epic.setEndTime();
                    epic.setDuration();
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Can't read form file");
        }
    }

    @Override
    public void createTask(Task task) {
        super.createTask(task);
        save();
    }

    @Override
    public void createSubtask(Subtask subtask, Epic epic) {
        super.createSubtask(subtask, epic);
        save();
    }

    @Override
    public void createEpic(Epic epic) {
        super.createEpic(epic);
        save();
    }

    @Override
    public void removeAllTask() {
        super.removeAllTask();
        save();
    }

    @Override
    public void removeAllSubtask() {
        super.removeAllSubtask();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void changeStatusToProgress(Task task) {
        super.changeStatusToProgress(task);
        save();
    }

    @Override
    public void changeStatusToDone(Task task) {
        super.changeStatusToDone(task);
        save();
    }
}

