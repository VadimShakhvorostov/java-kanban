import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

//        FileBackedTaskManager managers = new FileBackedTaskManager(new File("file.csv"));
//        managers.loadFromFile(new File("file.csv"));
//        TaskManager managers = Managers.getDefault();
//        System.out.println(managers.getEpicsById(1));
//        System.out.println(managers.getEpicsSubtasks(managers.getEpicsById(1)));
//        Task t1 = new Task("1-task", "1-task", LocalDateTime.of(2024, 5, 8, 4, 0), 30);
//        Task t2 = new Task("2-task", "2-task", LocalDateTime.of(2024, 5, 8, 6, 0), 30);
//        Task t3 = new Task("3-task", "3-task");
//        Epic e1 = new Epic("ехать", "eхать в деревню");
//        Subtask s1 = new Subtask("1", "1", LocalDateTime.of(2023, 5, 8, 4, 0), 60);
//        Subtask s2 = new Subtask("2", "2", LocalDateTime.of(2023, 5, 8, 8, 0), 120);
//        Subtask s3 = new Subtask("2", "2");

//        managers.createTask(t1);
//        managers.createTask(t2);
//        managers.createTask(t3);
//        managers.createEpic(e1);
//        managers.createSubtask(s1, e1);
//        managers.createSubtask(s2, e1);
//        managers.createSubtask(s3, e1);
//
//        System.out.println(managers.getPrioritizedTasks());
//        System.out.println(managers.getTasks());
//
//        Task t3 = new Task("3-task", "3-task",2);
//        managers.updateTask(t3);
//
//        System.out.println(managers.getPrioritizedTasks());
//        System.out.println(managers.getTasks());

//        Task t3 = new Task("сесть", "сесть на стул", LocalDateTime.of(2024, 5, 8, 2, 0), 60);
//        Task t4 = new Task("сесть", "сесть на стул");
//        managers.createTask(t3);
//        managers.createTask(t4);

//        System.out.println(managers.getPrioritizedTasks());

//        System.out.println(managers.getEpics());
//        System.out.println(managers.getSubtasks());

//        System.out.println(managers.getEpics());
//        System.out.println(managers.getEpicsSubtasks(e1));
//        Subtask s3 = new Subtask("бежать", "сделать работу", 2, LocalDateTime.of(2020, 1, 1, 3, 0), 60);
//        managers.updateSubtask(s3);
//        System.out.println(managers.getEpics());
//        System.out.println(managers.getEpicsSubtasks(e1));

//        Epic e1 = new Epic("ехать", "eхать в деревню");
//        Subtask s1 = new Subtask("сделать", "сделать работу");
//        Subtask s2 = new Subtask("сделать", "сделать работу");
//        managers.createEpic(e1);
//        managers.createSubtask(s1,e1);
//        managers.createSubtask(s2,e1);
//        System.out.println(managers.getEpics());
//        System.out.println(managers.getEpicsSubtasks(e1));
//        Subtask s3 = new Subtask("срать", "сделать работу",2);
//        managers.updateSubtask(s3);
//        System.out.println(managers.getEpics());
//        System.out.println(managers.getEpicsSubtasks(e1));


    }
}