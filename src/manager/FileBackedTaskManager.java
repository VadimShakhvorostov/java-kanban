package manager;

import tasks.*;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private File file;
    private static final String HEADER = "id,type,name,status,description,epic \n";

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
                final int id = Integer.parseInt(string[0]);
                final String name = string[2];
                final String description = string[4];
                final TaskStatus status = TaskStatus.valueOf(string[3]);
                final TaskType type = TaskType.valueOf(string[1]);
                switch (type.toString()) {
                    case "TASK":
                        Task task = new Task(name, description, id);
                        task.setStatus(status);
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
                        subtasks.put(subtask.getId(), subtask);
                        break;
                }
            }
            setSubtaskToEpic();
        } catch (IOException e) {
            throw new ManagerSaveException("Can't read form file");
        }
    }

    private void setSubtaskToEpic() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            if (bufferedReader.readLine().isEmpty()) {
                return;
            }
            while (bufferedReader.ready()) {
                String[] string = bufferedReader.readLine().split(",");
                if (string[1].equals("SUBTASK")) {
                    final int id = Integer.parseInt(string[0]);
                    final int epicSubtasks = Integer.parseInt(string[5]);
                    Subtask subtask = subtasks.get(id);
                    Epic epic = epics.get(epicSubtasks);
                    subtask.setEpic(epic);
                    epic.setSubtask(subtask);
                    epic.setStatus();
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

