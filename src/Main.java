
import manager.Managers;
import manager.TaskManager;
import tasks.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        Task t1 = new Task("1", "1");
        manager.createTask(t1);


        Task t2 = new Task("2", "2");
        manager.createTask(t2);


        Task t3 = new Task("3", "3");
        manager.createTask(t3);








        System.out.println(manager.getHistory());
    }
}