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

    private List<Integer> subtaskIdThisEpic = new ArrayList<>();

    public void setDuration() {
        if (startTime != null) {
            duration = (int) Duration.between(startTime, endTime).toMinutes();
        }
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

    public Epic(String name, String description, int id) {
        super(id, name, description);
    }

    public void removeSubtask(int id) {
        for (Integer subtaskId : subtaskIdThisEpic) {
            if (subtaskId == id) {
                subtaskIdThisEpic.remove(subtaskId);
                break;
            }
        }
    }

    public void removeEpicsSubtask() {
        subtaskIdThisEpic.clear();
    }

    public void setSubtaskIdThisEpic(List<Integer> subtaskIdThisEpic) {
        this.subtaskIdThisEpic = subtaskIdThisEpic;
    }

    public void setSubtask(Integer subtask) {
        if (!subtaskIdThisEpic.contains(subtask)) {
            subtaskIdThisEpic.add(subtask);
        }
    }

    public List<Integer> getSubtaskIdThisEpic() {
        return subtaskIdThisEpic;
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



