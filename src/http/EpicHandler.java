package http;

import com.sun.net.httpserver.HttpExchange;
import exception.ValidationException;
import manager.TaskManager;
import tasks.Epic;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

import java.util.Optional;

import static manager.Managers.gson;

public class EpicHandler extends BaseHttpHandler {
    private enum TaskEndpoint {
        GET_EPIC, GET_EPIC_ID, GET_EPIC_ID_SUBTASKS, POST_EPIC, DELETE_EPIC, UNKNOWN;
    }

    public EpicHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        TaskEndpoint endpoint = getEndpoint(path, requestMethod);
        Optional<Integer> taskId = getTaskIdFromRequest(exchange);
        Epic epic;
        switch (endpoint) {
            case GET_EPIC:
                response = gson.toJson(manager.getEpics());
                sendText(exchange, response, HttpURLConnection.HTTP_OK);
                return;
            case GET_EPIC_ID:

                if (taskId.isPresent()) {
                    epic = manager.getEpicsById(taskId.get());
                    if (epic != null) {
                        response = gson.toJson(manager.getEpicsById(taskId.get()));
                        sendText(exchange, response, HttpURLConnection.HTTP_OK);
                    } else {
                        sendNotFound(exchange, "Не найдено");
                    }
                } else {
                    sendNotFound(exchange, "Неверный формат");
                }
                return;
            case DELETE_EPIC:
                if (taskId.isPresent()) {
                    epic = manager.getEpicsById(taskId.get());
                    if (epic != null) {
                        manager.removeEpicById(taskId.get());
                        sendText(exchange, "Epic " + taskId.get() + " успешно удален", HttpURLConnection.HTTP_OK);
                    } else {
                        sendNotFound(exchange, "Не найдено");
                    }
                } else {
                    sendNotFound(exchange, "Неверный формат");
                }
                return;
            case POST_EPIC:
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                if (body.isEmpty()) {
                    sendNotFound(exchange, "Нет данных для создания Epic");
                    return;
                }
                epic = gson.fromJson(body, Epic.class);
                Integer id = epic.getId();
                try {
                    if (id == null) {
                        manager.createEpic(epic);
                        sendText(exchange, "Epic " + epic.getId() + " успешно создан", HttpURLConnection.HTTP_OK);
                    } else {
                        manager.updateEpic(epic);
                        sendText(exchange, "Epic " + epic.getId() + " успешно обновлен", HttpURLConnection.HTTP_CREATED);
                    }
                    return;
                } catch (FileNotFoundException e) {
                    sendNotFound(exchange, "Нат задачи с таким id");
                } catch (ValidationException e) {
                    sendHasInteractions(exchange);
                }
            case GET_EPIC_ID_SUBTASKS:
                if (taskId.isPresent()) {
                    if (manager.getEpicsById(taskId.get()) != null) {
                        epic = manager.getEpicsById(taskId.get());
                        sendText(exchange, gson.toJson(manager.getEpicsSubtasks(epic)), HttpURLConnection.HTTP_OK);
                    } else {
                        sendNotFound(exchange, "Не найдено");
                    }

                } else {
                    sendNotFound(exchange, "Неверный формат id");
                }
                return;
            default:
                sendNotFound(exchange, "Некорректный запрос");
        }
    }

    private TaskEndpoint getEndpoint(String requestPath, String requestMethod) {
        String[] pathParts = requestPath.split("/");
        if (requestMethod.equals("POST")) {
            return TaskEndpoint.POST_EPIC;
        } else if (requestMethod.equals("DELETE")) {
            return TaskEndpoint.DELETE_EPIC;
        } else if (pathParts.length == 2 && requestMethod.equals("GET")) {
            return TaskEndpoint.GET_EPIC;
        } else if (pathParts.length == 3) {
            return TaskEndpoint.GET_EPIC_ID;
        } else if (pathParts.length > 3) {
            return TaskEndpoint.GET_EPIC_ID_SUBTASKS;
        } else
            return TaskEndpoint.UNKNOWN;
    }
}

