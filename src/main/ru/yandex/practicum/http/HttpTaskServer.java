package main.ru.yandex.practicum.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import main.ru.yandex.practicum.http.adapters.DurationAdapter;
import main.ru.yandex.practicum.http.adapters.LocalDateTimeAdapter;
import main.ru.yandex.practicum.http.handlers.*;
import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;


public class HttpTaskServer {
    private static final int PORT = 8080;
    private final TaskManager manager;
    HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 5);

    public HttpTaskServer(TaskManager taskManager) throws IOException {
        this.manager = taskManager;
    }

    public static void main(String[] args) throws IOException {

    }

    public void start() throws IOException {
        server.start();
        server.createContext("/task", new TaskHandler(manager));
        server.createContext("/subtask", new SubTaskHandler(manager));
        server.createContext("/epic", new EpicHandler(manager));
        server.createContext("/history", new HistroyHandler(manager));
        server.createContext("/prioritized", new PrioritizedHandler(manager));
    }

    public void stop() {
        server.stop(0);
    }

    public static Gson getGson() {
        return new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
    }
}
