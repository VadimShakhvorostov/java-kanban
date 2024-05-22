package http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import manager.TaskManager;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class BaseHttpHandler implements HttpHandler {

    TaskManager manager;
    String response;

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
    }

    protected void sendText(HttpExchange exchange, String responseString) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(200, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange, String responseString) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(404, 0);
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
        exchange.close();
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        try (OutputStream os = exchange.getResponseBody()) {
            exchange.sendResponseHeaders(406, 0);
            os.write("Задачи пересекаются по времени".getBytes(StandardCharsets.UTF_8));
        }
        exchange.close();
    }

    protected Optional<Integer> getTaskIdFromRequest(HttpExchange exchange) {
        String[] pathParts = exchange.getRequestURI().getPath().split("/");
        try {
            return Optional.of(Integer.parseInt(pathParts[2]));
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException exception) {
            return Optional.empty();
        }
    }
}
