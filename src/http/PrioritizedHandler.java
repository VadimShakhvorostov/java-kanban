package http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import manager.TaskManager;

import java.io.IOException;
import java.time.LocalDateTime;

public class PrioritizedHandler extends BaseHttpHandler {

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .serializeNulls()
            .create();

    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        if (requestMethod.equals("GET")) {
            sendText(exchange, gson.toJson(manager.getPrioritizedTasks()), 200);
        } else {
            sendNotFound(exchange, "Некорректный запрос");
        }
    }
}


