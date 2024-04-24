package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;

    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            String header = "id,type,name,status,description,epic \n";
            writer.write(header);
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
            throw new ManagerSaveException("Ошибка сохранения");
        }
    }

    public void loadFromFile(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (bufferedReader.readLine().isEmpty()) {
                return;
            }
            while (bufferedReader.ready()) {
                String[] string = bufferedReader.readLine().split(",");
                switch (string[1]) {
                    case "TASK":
                        Task task = new Task(string[2], string[4], Integer.parseInt(string[0]));
                        task.setStatus(TaskStatus.valueOf(string[3]));
                        tasks.put(task.getId(), task);
                        break;
                    case "EPIC":
                        Epic epic = new Epic(string[2], string[4], Integer.parseInt(string[0]));
                        epic.setStatus();
                        epics.put(epic.getId(), epic);
                        break;
                    case "SUBTASK":
                        Subtask subtask = new Subtask(string[2], string[4], Integer.parseInt(string[0]));
                        subtask.setStatus(TaskStatus.valueOf(string[3]));
                        subtask.setEpic(getEpicsById(Integer.parseInt(string[5])));
                        subtasks.put(subtask.getId(), subtask);
                        break;
                }
            }
            SetSubtaskToTask();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SetSubtaskToTask() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (bufferedReader.readLine().isEmpty()) {
                return;
            }
            while (bufferedReader.ready()) {
                String[] string = bufferedReader.readLine().split(",");
                if (string[1].equals("SUBTASK")) {
                    Subtask subtask = subtasks.get(Integer.parseInt(string[0]));
                    Epic epic = epics.get(Integer.parseInt(string[5]));
                    subtask.setEpic(epic);
                    epic.setSubtask(subtask);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public void updateSubTask(Subtask newSubtask) {
        super.updateSubTask(newSubtask);
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
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
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

