package tasks;

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

    public Subtask(String name, String description, int id) {
        super(name, description, id);
        this.epic = new Epic(null, null);
    }

    @Override
    public String toString() {
        return getId() + "," + TaskType.SUBTASK + "," + getName() + "," + getStatus() + "," + getDescription() + "," + getEpic().getId() + ",\n";
    }
}
