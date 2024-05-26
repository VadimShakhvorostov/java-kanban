import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import http.LocalDateTimeAdapter;
import http.TaskHandler;
import manager.FileBackedTaskManager;
import manager.Managers;
import manager.TaskManager;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

//        FileBackedTaskManager managers = new FileBackedTaskManager(new File("file.csv"));
//        managers.loadFromFile(new File("file.csv"));
        TaskManager managers = Managers.getDefault();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

        Task task1 = new Task("task-1","task-1", LocalDateTime.now(),30);
        managers.createTask(task1);
        Task task2 = new Task(1,"task-2","task-2",LocalDateTime.of(2020, 1, 1, 1, 0),30);
        managers.createTask(task2);

//
//        Epic epic = new Epic("epic-1", "epic-1");
//        managers.createEpic(epic);
//
//        Subtask subtask2 = new Subtask("subtask-3", "subtask-3", LocalDateTime.of(2020, 1, 1, 1, 0),30, epic.getId());
//        managers.createSubtask(subtask2);
//        Subtask subtask3 = new Subtask("subtask-3", "subtask-3",LocalDateTime.of(2020, 1, 1, 2, 0),30, epic.getId());
//        managers.createSubtask(subtask3);
//        Subtask subtask4 = new Subtask("subtask-4", "subtask-4", epic.getId());
//        managers.createSubtask(subtask4);
//
//        System.out.println("*******************************");
//        System.out.println(managers.getTasks());
//        System.out.println(managers.getEpics());
//        System.out.println(managers.getSubtasks());
//        System.out.println("*******************************");
//
//        Subtask subtask5 = new Subtask(subtask2.getId(), "subtask-5", "subtask-5",LocalDateTime.of(2020, 1, 1, 1, 0),30);
//        managers.updateSubtask(subtask5);
//        System.out.println(managers.getEpics());
//        System.out.println(managers.getSubtasks());

//        Subtask subtask5 = new Subtask(3,"subtask-5","subtask-5");
//        Subtask subtask6 = new Subtask(4,"subtask-6","subtask-6");
//        Subtask subtask7 = new Subtask(5,"subtask-7","subtask-7");
//
//        System.out.println(managers.getTasks());
//        System.out.println(managers.getSubtasks());
//        System.out.println(managers.getEpics());



//        TaskManager managers = Managers.getDefault();
//        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
//        httpServer.createContext("/tasks", new TaskHandler(managers));
//        httpServer.start();
//        System.out.println("HTTP-сервер запущен на " + 8080 + " порту!");
//        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), 50);
//        String taskJson = gson.toJson(task);
//        System.out.println(taskJson);
//
//        HttpClient client = HttpClient.newHttpClient();
//        URI url = URI.create("http://localhost:8080/tasks");
//
//        HttpRequest request = HttpRequest
//                .newBuilder()
//                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
//                .uri(url)
//                .build();
//
//
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(managers.getTasks());

    }
}