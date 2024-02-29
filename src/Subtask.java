public class Subtask extends Task {

    private Epic subtasksEpics;

    public Epic getSubtasksEpics() {
        return subtasksEpics;
    }

    public void setSubtasksEpics(Epic subtasksEpics) {
        this.subtasksEpics = subtasksEpics;
    }

    public Subtask(String name, String description) {
        super(name, description);
        subtasksEpics = new Epic(null,null);
    }
}
