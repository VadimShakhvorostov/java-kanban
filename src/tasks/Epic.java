package tasks;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private LocalDateTime endTime;

    private List<Integer> subtaskId = new ArrayList<>();

    public void setDuration() {
        if (startTime != null) {
            duration = (int) Duration.between(startTime, endTime).toMinutes();
        }
    }

    public void setSubtask(Integer subtask) {
        subtaskId.add(subtask);
    }

    @Override
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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

    public Epic(int id, String name, String description) {
        super(id, name, description);
    }

    public void removeSubtask(int id) {
        for (Integer subtaskId : subtaskId) {
            if (subtaskId == id) {
                this.subtaskId.remove(subtaskId);
                break;
            }
        }
    }

    public void removeEpicsSubtask() {
        subtaskId.clear();
    }

    public void setSubtaskId(List<Integer> subtaskId) {
        this.subtaskId = subtaskId;
    }


    public List<Integer> getSubtaskId() {
        return subtaskId;
    }

    @Override
    public Integer getId() {
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



