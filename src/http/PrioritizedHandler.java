package http;

import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;
import java.net.HttpURLConnection;

import static manager.Managers.gson;

public class PrioritizedHandler extends BaseHttpHandler {

    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("GET")) {
            sendText(exchange, gson.toJson(manager.getPrioritizedTasks()), HttpURLConnection.HTTP_OK);
        } else {
            sendNotFound(exchange, "Некорректный запрос");
        }
    }
}


