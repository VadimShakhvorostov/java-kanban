import manager.FileBackedTaskManager;

import java.io.File;

public class Main {

    public static void main(String[] args) {


        FileBackedTaskManager fileManager = new FileBackedTaskManager(new File("file.csv"));


//        Task t1 = new Task("сделать", "сделать работу");
//        fileManager.createTask(t1);
//        Task t2 = new Task("сделать", "сделать работу");
//        fileManager.createTask(t2);
//        Task t3 = new Task("сделать", "сделать работу");
//        fileManager.createTask(t3);
//
//
//        Epic e1 = new Epic("мыть", "мыть пол");
//        fileManager.createEpic(e1);
//
//        Epic e2 = new Epic("мыть", "мыть пол");
//        fileManager.createEpic(e2);
//
//        Subtask s1 = new Subtask("пить", "пить сок");
//        Subtask s2 = new Subtask("пить", "пить сок");
//        fileManager.createSubtask(s1,e1);
//        fileManager.createSubtask(s2,e2);
//
//        fileManager.changeStatusToDone(t1);
//        fileManager.changeStatusToDone(t2);

        fileManager.loadFromFile(new File("file.csv"));

        System.out.println(fileManager.getTasks());
        System.out.println(fileManager.getEpics());
        System.out.println(fileManager.getSubtasks());
    }
}