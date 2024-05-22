package http;

import com.sun.net.httpserver.HttpServer;
import exception.ValidationException;
import manager.Managers;
import manager.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;

public class HttpTaskServer {

    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {

        TaskManager managers = Managers.getDefault();
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks", new TaskHandler(managers));
        httpServer.createContext("/subtasks", new SubtaskHandler(managers));
        httpServer.start();
        System.out.println("HTTP-сервер запущен на " + PORT + " порту!");
    }


}

