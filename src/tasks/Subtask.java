package tasks;

import java.time.LocalDateTime;

public class Subtask extends Task {

    private Epic epic;

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }

    public Subtask(String name, String description) {
        super(name, description);
        epic = new Epic(null, null);
    }

    public Subtask(String name, String description, int id, LocalDateTime startTime, int duration) {
        super(name, description, startTime, duration, id);
        this.epic = new Epic(null, null);
    }

    public Subtask(String name, String description, LocalDateTime startTime, int duration) {
        super(name, description, startTime, duration);
    }

    public Subtask(String name, String description, LocalDateTime startTime, int duration, int id) {
        super(name, description, startTime, duration, id);
        this.epic = new Epic(null, null);
    }

    public Subtask(String name, String description, int id) {
        super(name, description, id);
        this.epic = new Epic(null, null);
    }

    @Override
    public String toString() {
        if (getStartTime() != null) {
            return getId() + "," + TaskType.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription()

                    + "," + getStartTime().format(DATE_TIME_FORMATTER) + "," + getEndTime().format(DATE_TIME_FORMATTER)
                    + "," + getDuration() + "," + getEpic().getId() + ",\n";
        } else
            return getId() + "," + TaskType.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription() + "," + getEpic().getId() + ",\n";
    }
}
