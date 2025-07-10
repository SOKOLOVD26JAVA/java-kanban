package main.ru.yandex.practicum.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import main.ru.yandex.practicum.http.adapters.DurationAdapter;
import main.ru.yandex.practicum.http.adapters.LocalDateTimeAdapter;
import main.ru.yandex.practicum.http.handlers.*;
import main.ru.yandex.practicum.manager.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.logging.Handler;


public class HttpTaskServer {
    protected final int PORT;
    protected final TaskManager manager;
    protected HttpServer server = HttpServer.create();

    public HttpTaskServer(TaskManager taskManager, int port) throws IOException {
        this.manager = taskManager;
        this.PORT = port;
        server.bind(new InetSocketAddress(PORT), 5);
//          По поводу создания контекста немного не понял, еще был такой вариант, тут соответственно в конструктор String text передаю..)
//        switch (text){
//            case "/task" -> createContextTask();
//            case "/subTask" -> createContextSubTask();
//            case "/epic" -> createContextEpic();
//            case "/history" -> createContextHistory();
//            case "/prioritized" -> createContextPrioritized();
//            default -> System.out.println("Неизвестный контекст");
//        }
    }

    public void start() throws IOException {
        server.start();
    }

    public void createContextTask() {
        server.createContext("/task", new TaskHandler(manager));
    }

    public void createContextSubTask() {
        server.createContext("/subtask", new SubTaskHandler(manager));
    }

    public void createContextEpic() {
        server.createContext("/epic", new EpicHandler(manager));
    }

    public void createContextHistory() {
        server.createContext("/history", new HistroyHandler(manager));
    }

    public void createContextPrioritized() {
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
