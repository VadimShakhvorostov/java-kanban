import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.LocalDateTimeAdapter;
import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

//        FileBackedTaskManager managers = new FileBackedTaskManager(new File("file.csv"));
//        managers.loadFromFile(new File("file.csv"));

        TaskManager managers = Managers.getDefault();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

        Task task1 = new Task("task-1","task-1");
        managers.createTask(task1);

        Task task2 = new Task(1,"task-2","task-2");

        System.out.println(managers.getTasks());




//
//
//        Epic epic = new Epic("epic-1", "epic-1");
//        managers.createEpic(epic);
//        System.out.println(managers.getEpics());
//
//
//        Subtask subtask2 = new Subtask("subtask-3","subtask-3",2);
//        managers.createSubtask(subtask2);
//        Subtask subtask3 = new Subtask("subtask-3","subtask-3",2);
//        managers.createSubtask(subtask3);
//        Subtask subtask4 = new Subtask("subtask-4","subtask-4",2);
//        managers.createSubtask(subtask4);
//
//        Subtask subtask5 = new Subtask(3,"subtask-5","subtask-5");
//        Subtask subtask6 = new Subtask(4,"subtask-6","subtask-6");
//        Subtask subtask7 = new Subtask(5,"subtask-7","subtask-7");
//
//
//        System.out.println(managers.getTasks());
//        System.out.println(managers.getSubtasks());
//        System.out.println(managers.getEpics());




    }
}