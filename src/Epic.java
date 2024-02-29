import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {

    private final ArrayList<Subtask> epicsSubtask;

    public Epic(String name, String description) {
        super(name, description);
        epicsSubtask = new ArrayList<>();
    }

    public void addSubtask(Subtask subtask) {
        epicsSubtask.add(subtask);
    }

    public ArrayList<Subtask> getEpicsSubtask() {
        return epicsSubtask;
    }

    public void setStatus() {
        super.setStatus(TaskStatus.DONE);
        if (!epicsSubtask.isEmpty()) {
            for (Subtask s : epicsSubtask) {
                if (s.getStatus() != TaskStatus.DONE) {
                    super.setStatus(TaskStatus.IN_PROGRESS);
                    return;
                }
            }
        } else super.setStatus(TaskStatus.NEW);
    }
}



