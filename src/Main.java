import manager.InMemoryTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

public class Main {

    public static void main(String[] args) {

        TaskManager manager = Managers.getDefault();

        Task t1 = new Task("1", "1");
        manager.createTask(t1);
        manager.getTasksById(1);
        System.out.println(manager.getHistory());

        Task t2 = new Task("sdfsdf", "2sdfsdfsdf", 1);
        manager.updateTask(t2);
        manager.getTasksById(1);



        System.out.println(manager.getTasksById(5));

















    }

}