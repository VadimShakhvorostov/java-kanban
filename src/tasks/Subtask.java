package tasks;

import java.time.LocalDateTime;

public class Subtask extends Task {

    private Integer epicId;

    public Integer getEpicId() {
        return epicId;
    }

    public void setEpicId(Integer epicId) {
        this.epicId = epicId;
    }


    public Subtask(String name, String description, Integer epicId) {
        super(name, description);
        this.epicId = epicId;
    }

    public Subtask(String name, String description, LocalDateTime startTime, int duration, Integer epicId) {
        super(name, description, startTime, duration);
        this.epicId = epicId;
    }

    public Subtask(int id, String name, String description) {
        super(id, name, description);
    }

    public Subtask(int id, String name, String description, LocalDateTime startTime, int duration) {
        super(id,name, description, startTime, duration);
    }

    @Override
    public String toString() {
        if (getStartTime() != null) {
            return getId() + "," + TaskType.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription()
                    + "," + epicId + "," + getStartTime().format(DATE_TIME_FORMATTER) + "," + getEndTime().format(DATE_TIME_FORMATTER)
                    + "," + getDuration() +  ",\n";
        } else
            return getId() + "," + TaskType.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription() + "," + epicId + ",\n";
    }
}
