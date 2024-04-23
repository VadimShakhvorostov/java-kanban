package tasks;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {

    private List<Subtask> epicsSubtask = new ArrayList<>();

    public Epic(String name, String description) {
        super(name, description);
    }

    public Epic(String name, String description, int id) {
        super(name, description, id);
    }

    public void removeSubtask(int id) {
        for (Subtask s : epicsSubtask) {
            if (s.getId() == id) {
                epicsSubtask.remove(s);
                break;
            }
        }
    }

    public void removeEpicsSubtask() {
        epicsSubtask.clear();
    }

    public void setEpicsSubtask(List<Subtask> epicsSubtask) {
        this.epicsSubtask = epicsSubtask;
    }

    public void setSubtask(Subtask subtask) {
        epicsSubtask.add(subtask);
    }

    public void replaceSubtask(Subtask subtask) {
        for (Subtask s : epicsSubtask) {
            if (s.getId() == subtask.getId()) {
                epicsSubtask.remove(s);
                epicsSubtask.add(subtask);
            }
        }
    }

    public List<Subtask> getEpicsSubtask() {
        return epicsSubtask;
    }

    public void setStatus() {
        int countDone = 0;
        int countNew = 0;
        if (!epicsSubtask.isEmpty()) {
            for (Subtask s : epicsSubtask) {
                if (s.getStatus() == TaskStatus.DONE) {
                    countDone++;
                }
                if (s.getStatus() == TaskStatus.NEW) {
                    countNew++;
                }
            }
            if (countNew == epicsSubtask.size()) {
                super.setStatus(TaskStatus.NEW);
            } else if (countDone == epicsSubtask.size()) {
                super.setStatus(TaskStatus.DONE);
            } else
                super.setStatus(TaskStatus.IN_PROGRESS);
        } else super.setStatus(TaskStatus.NEW);
    }

    @Override
    public int getId() {
        return super.getId();
    }

    @Override
    public TaskStatus getStatus() {
        return super.getStatus();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String toString() {
        return getId() + "," + TaskType.EPIC + "," + getName() + "," + getStatus() + "," + getDescription() + ",\n";
    }
}



