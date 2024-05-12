package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Epic extends Task {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int duration;

    private List<Subtask> epicsSubtask = new ArrayList<>();


    public void setStartTime() {
        startTime = epicsSubtask.stream()
                .filter(subtask -> subtask.getStartTime() != null)
                .min(Comparator.comparing(Subtask::getStartTime))
                .map(subtask -> subtask.getStartTime())
                .orElse(null);

    }

    public void setEndTime() {
        endTime = epicsSubtask.stream()
                .filter(subtask -> subtask.getEndTime() != null)
                .max(Comparator.comparing(Subtask::getEndTime))
                .map(subtask -> subtask.getEndTime())
                .orElse(null);

    }

    public void setDuration() {
        if (startTime != null) {
            duration = (int) Duration.between(startTime, endTime).toMinutes();
        }
    }

    @Override
    public LocalDateTime getStartTime() {

        return startTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public int getDuration() {
        return duration;
    }

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
                break;
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
        if (startTime != null) {
            return getId() + "," + TaskType.EPIC + "," + getName() + "," + getStatus() + "," + getDescription()
                    + "," + getStartTime().format(DATE_TIME_FORMATTER) + "," + getEndTime().format(DATE_TIME_FORMATTER) + "," + getDuration() + ",\n";
        } else
            return getId() + "," + TaskType.EPIC + "," + getName() + "," + getStatus() + "," + getDescription() + ",\n";
    }
}



