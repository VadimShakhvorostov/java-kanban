package http;

import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import exception.ValidationException;
import manager.TaskManager;
import com.google.gson.Gson;
import tasks.Task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;


public class TaskHandler extends BaseHttpHandler {
    private enum TaskEndpoint {
        GET_TASK, GET_TASK_ID, POST_TASK, DELETE_TASK, UNKNOWN;
    }

    private final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter()).create();

    public TaskHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        TaskEndpoint endpoint = getEndpoint(path, requestMethod);
        Optional<Integer> taskId = getTaskIdFromRequest(exchange);
        switch (endpoint) {
            case GET_TASK:
                response = gson.toJson(manager.getTasks());
                sendText(exchange, response);
                return;
            case GET_TASK_ID:

                if (taskId.isPresent()) {
                    Task task = manager.getTasksById(taskId.get());
                    if (task != null) {
                        response = gson.toJson(manager.getTasksById(taskId.get()));
                        sendText(exchange, response);
                    } else {
                        sendNotFound(exchange, "Не найдено");
                    }
                } else {
                    sendNotFound(exchange, "Плохой формат");
                }
                return;
            case DELETE_TASK:
                if (taskId.isPresent()) {
                    Task task = manager.getTasksById(taskId.get());
                    if (task != null) {
                        manager.removeTaskById(taskId.get());
                        sendText(exchange, "Task " + taskId.get() + " успешно удален");
                    } else {
                        sendNotFound(exchange, "Не найдено");
                    }
                } else {
                    sendNotFound(exchange, "Плохой формат");
                }
                return;
            case POST_TASK:
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                if (body.isEmpty()) {
                    sendNotFound(exchange, "Нет данных для создания Task");
                    return;
                }
                Task task = gson.fromJson(body, Task.class);
                Integer id = task.getId();
                try {
                    if (id == null) {
                        manager.createTask(task);
                        sendText(exchange, "Task " + task.getId() + " успешно создан");
                    } else {
                        manager.updateTask(task);
                        sendText(exchange, "Task " + task.getId() + " успешно обновлен");
                    }
                    return;
                } catch (FileNotFoundException e) {
                    sendNotFound(exchange, "Нат задачи с таким id");
                } catch (ValidationException e) {
                    sendHasInteractions(exchange);
                }
            default:
                sendNotFound(exchange, "Некорректный запрос");
        }
    }

    private TaskEndpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        System.out.println(pathParts.length);
        if (requestMethod.equals("POST")) {
            return TaskEndpoint.POST_TASK;
        } else if (requestMethod.equals("DELETE")) {
            return TaskEndpoint.DELETE_TASK;
        } else if (pathParts.length == 2 && requestMethod.equals("GET")) {
            return TaskEndpoint.GET_TASK;
        } else if (pathParts.length > 2) {
            return TaskEndpoint.GET_TASK_ID;
        } else
            return TaskEndpoint.UNKNOWN;
    }
}

