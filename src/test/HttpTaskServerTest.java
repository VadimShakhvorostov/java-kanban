package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import http.*;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

public class HttpTaskServerTest {
    private static final int PORT = 8080;
    TaskManager managers;
    HttpServer httpServer;
    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .serializeNulls()
            .create();

    @BeforeEach
    void startServer() throws IOException {
        managers = Managers.getDefault();
        httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/tasks", new TaskHandler(managers));
        httpServer.createContext("/subtasks", new SubtaskHandler(managers));
        httpServer.createContext("/epic", new EpicHandler(managers));
        httpServer.createContext("/history", new HistoryHandler(managers));
        httpServer.createContext("/prioritized", new PrioritizedHandler(managers));
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + 8080 + " порту!");
    }

    @AfterEach
    void stopServer() throws IOException {
        httpServer.stop(1);
    }

    @Test
    public void testAddTask() throws IOException, InterruptedException {
        Task task = new Task("Test task 2", "Testing task 2", LocalDateTime.now(), 50);
        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, managers.getTasks().size());
        assertEquals("Test task 2", managers.getTasksById(1).getName());
    }

    @Test
    public void testAddEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Test epic 1", "Test epic 1");
        String epicJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic");
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(epicJson))
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, managers.getEpics().size());
        assertEquals("Test epic 1", managers.getEpicsById(1).getName());
    }

    @Test
    public void testAddSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Test epic 1", "Test epic 1");
        managers.createEpic(epic);
        Subtask subtask = new Subtask("Test subtask 2", "Test subtask 2", epic.getId());
        String taskJson = gson.toJson(subtask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest requestEpic = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(1, managers.getSubtasks().size());
        assertEquals("Test subtask 2", managers.getSubTasksById(2).getName());
    }

    @Test
    public void testRemoveTask() throws IOException, InterruptedException {
        Task task = new Task("Test 2", "Testing task 2", LocalDateTime.now(), 50);
        managers.createTask(task);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request = HttpRequest
                .newBuilder()
                .DELETE()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(0, managers.getTasks().size());
    }

    @Test
    public void testRemoveEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Test 2", "Testing task 2");
        managers.createEpic(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic/1");
        HttpRequest request = HttpRequest
                .newBuilder()
                .DELETE()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(0, managers.getEpics().size());
    }

    @Test
    public void testRemoveSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Test epic 1", "Test epic 1");
        managers.createEpic(epic);
        Subtask subtask = new Subtask("Test subtask 2", "Test subtask 2", epic.getId());
        managers.createSubtask(subtask);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest requestEpic = HttpRequest
                .newBuilder()
                .DELETE()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(requestEpic, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(0, managers.getSubtasks().size());
    }

    @Test
    public void testUpdateTask() throws IOException, InterruptedException {
        Task task = new Task("Test task 1", "Test task 1", LocalDateTime.now(), 50);
        managers.createTask(task);
        Task taskToUpdate = new Task(1, "Test task 2", "Test task 2", LocalDateTime.now(), 50);
        String taskJson = gson.toJson(taskToUpdate);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertEquals(1, managers.getTasks().size());
        assertEquals("Test task 2", managers.getTasksById(1).getName());
    }

    @Test
    public void testUpdateEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Test task 1", "Test task 1");
        managers.createEpic(epic);
        Epic epic2 = new Epic(1, "Test task 2", "Test task 2");
        String taskJson = gson.toJson(epic2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic");
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertEquals(1, managers.getEpics().size());
        assertEquals("Test task 2", managers.getEpicsById(1).getName());
    }

    @Test
    public void testUpdateSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Test task 1", "Test task 1");
        managers.createEpic(epic);
        Subtask subtask2 = new Subtask("Test subtask 2", "Test subtask 2", LocalDateTime.now(), 30, epic.getId());
        managers.createSubtask(subtask2);
        Subtask subtaskToUpdate = new Subtask(subtask2.getId(), "Test subtask updated", "Test subtask updated", LocalDateTime.now(), 30);
        String taskJson = gson.toJson(subtaskToUpdate);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(taskJson))
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode());
        assertEquals(1, managers.getSubtasks().size());
        assertEquals("Test subtask updated", managers.getSubTasksById(2).getName());
    }

    @Test
    public void testGetTask() throws IOException, InterruptedException {
        Task task = new Task("Test task 1", "Testing task 1", LocalDateTime.now(), 50);
        Task task2 = new Task("Test task 2", "Testing task 2", LocalDateTime.of(2024,5,27,1,0,0), 50);
        managers.createTask(task);
        managers.createTask(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String tasks = response.body();
        List<Task> taskList = gson.fromJson(tasks, new TaskListTypeToken().getType());
        assertEquals(200, response.statusCode());
        assertEquals(taskList.size(), managers.getTasks().size());
        assertEquals("Test task 1", managers.getTasksById(1).getName());
        assertEquals("Test task 2", managers.getTasksById(2).getName());

    }
    @Test
    public void testGetEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic("Test epic 1", "Test epic 1");
        Epic epic2 = new Epic("Test epic 2", "Test epic 2");
        managers.createEpic(epic1);
        managers.createEpic(epic2);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic");
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String tasks = response.body();
        List<Task> taskList = gson.fromJson(tasks, new TaskListTypeToken().getType());
        assertEquals(200, response.statusCode());
        assertEquals(taskList.size(), managers.getEpics().size());
        assertEquals("Test epic 1", managers.getEpicsById(1).getName());
        assertEquals("Test epic 2", managers.getEpicsById(2).getName());
    }

    @Test
    public void testGetSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Test epic 1", "Test epic 1");
        managers.createEpic(epic);
        Subtask subtask2 = new Subtask("Test subtask 2", "Test subtask 2",
                LocalDateTime.now(), 30, epic.getId());
        Subtask subtask3 = new Subtask("Test subtask 3", "Test subtask 3",
                LocalDateTime.of(2024,5,27,1,0,0),30,epic.getId());
        managers.createSubtask(subtask2);
        managers.createSubtask(subtask3);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/subtasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String tasks = response.body();
        List<Task> taskList = gson.fromJson(tasks, new TaskListTypeToken().getType());
        assertEquals(200, response.statusCode());
        assertEquals(taskList.size(), managers.getSubtasks().size());
        assertEquals("Test subtask 2", managers.getSubTasksById(2).getName());
        assertEquals("Test subtask 3", managers.getSubTasksById(3).getName());
    }

    @Test
    public void testGetSubtaskEpicsById() throws IOException, InterruptedException {
        Epic epic = new Epic("Test epic 1", "Test epic 1");
        managers.createEpic(epic);
        Subtask subtask2 = new Subtask("Test subtask 2", "Test subtask 2",
                LocalDateTime.now(), 30, epic.getId());
        Subtask subtask3 = new Subtask("Test subtask 3", "Test subtask 3",
                LocalDateTime.of(2024,5,27,1,0,0),30,epic.getId());
        managers.createSubtask(subtask2);
        managers.createSubtask(subtask3);
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epic/1/subtasks");
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(url)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String tasks = response.body();
        List<Task> taskList = gson.fromJson(tasks, new TaskListTypeToken().getType());
        assertEquals(200, response.statusCode());
        assertEquals(taskList.size(), managers.getSubtasks().size());
        assertEquals(epic.getId(), managers.getSubTasksById(2).getEpicId());
        assertEquals(epic.getId(), managers.getSubTasksById(3).getEpicId());
    }
}
