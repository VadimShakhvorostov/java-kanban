package tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task {

    private String name;
    private String description;
    private Integer id;
    private TaskStatus status;
    private LocalDateTime startTime;
    private int duration;
    static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm dd.MM.yy");


    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Task( int id,String name, String description) {
        this.name = name;
        this.description = description;
        this.id = id;
    }

    public Task(String name, String description, LocalDateTime startTime, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(int id,String name, String description, LocalDateTime startTime, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.startTime = startTime;
        this.id = id;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    public void setDuration(int duration) {
        this.duration = duration;
    }

    public LocalDateTime getEndTime() {
        LocalDateTime endTime = null;
        if (startTime != null) {
            endTime = startTime.plusMinutes(duration);
        }
        return endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    public Integer getId() {
        return id;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        if (startTime != null) {
            return id + "," + TaskType.TASK + "," + name + "," + status + "," + description
                    + "," + startTime.format(DATE_TIME_FORMATTER) + "," + getEndTime().format(DATE_TIME_FORMATTER) + "," + duration + ",\n";
        } else
            return id + "," + TaskType.TASK + "," + name + "," + status + "," + description + ",\n";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return Objects.equals(id, task.id) && Objects.equals(name, task.name) && Objects.equals(description, task.description); //&& status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }
}

