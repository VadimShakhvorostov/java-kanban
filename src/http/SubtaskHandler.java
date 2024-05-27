package http;

import com.sun.net.httpserver.HttpExchange;
import exception.ValidationException;
import manager.TaskManager;
import tasks.Subtask;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import java.util.Optional;

import static manager.Managers.gson;

public class SubtaskHandler extends BaseHttpHandler {

    private enum SubtaskEndpoint {
        GET_SUBTASK, GET_SUBTASK_ID, POST_SUBTASK, DELETE_SUBTASK, UNKNOWN;
    }

    public SubtaskHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        SubtaskEndpoint endpoint = getEndpoint(path, requestMethod);
        Optional<Integer> subtaskId = getTaskIdFromRequest(exchange);
        switch (endpoint) {

            case GET_SUBTASK:
                response = gson.toJson(manager.getSubtasks());
                sendText(exchange, response, HttpURLConnection.HTTP_OK);
                return;
            case GET_SUBTASK_ID:
                if (subtaskId.isPresent()) {
                    Subtask subtask = manager.getSubTasksById(subtaskId.get());
                    if (subtask != null) {
                        response = gson.toJson(manager.getSubTasksById(subtaskId.get()));
                        sendText(exchange, response, HttpURLConnection.HTTP_OK);
                    } else {
                        sendNotFound(exchange, "Не найдено");
                    }
                } else {
                    sendNotFound(exchange, "Неверный формат");
                }
                return;
            case DELETE_SUBTASK:
                if (subtaskId.isPresent()) {
                    Subtask subtask = manager.getSubTasksById(subtaskId.get());
                    if (subtask != null) {
                        manager.removeSubtaskById(subtaskId.get());
                        sendText(exchange, "Subtask " + subtaskId.get() + " успешно удален", HttpURLConnection.HTTP_OK);
                    } else {
                        sendNotFound(exchange, "Не найдено");
                    }
                } else {
                    sendNotFound(exchange, "Неверный формат");
                }
                return;
            case POST_SUBTASK:
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                if (body.isEmpty()) {
                    sendNotFound(exchange, "Нет данных для создания Subtask");
                    return;
                }
                try {
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    Integer id = subtask.getId();

                    if (id == null) {
                        manager.createSubtask(subtask);
                        sendText(exchange, "Subtask " + subtask.getId() + " успешно создан", HttpURLConnection.HTTP_OK);
                    } else {
                        manager.updateSubtask(subtask);
                        sendText(exchange, "Subtask " + subtask.getId() + " успешно обновлен", HttpURLConnection.HTTP_CREATED);
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

    private SubtaskEndpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        if (requestMethod.equals("POST")) {
            return SubtaskEndpoint.POST_SUBTASK;
        } else if (requestMethod.equals("DELETE")) {
            return SubtaskEndpoint.DELETE_SUBTASK;
        } else if (pathParts.length == 2 && requestMethod.equals("GET")) {
            return SubtaskEndpoint.GET_SUBTASK;
        } else if (pathParts.length > 2) {
            return SubtaskEndpoint.GET_SUBTASK_ID;
        } else
            return SubtaskEndpoint.UNKNOWN;
    }
}

