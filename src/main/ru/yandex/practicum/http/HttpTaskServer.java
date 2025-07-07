package main.ru.yandex.practicum.http;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import main.ru.yandex.practicum.http.adapters.DurationAdapter;
import main.ru.yandex.practicum.http.adapters.LocalDateTimeAdapter;
import main.ru.yandex.practicum.http.handlers.EpicHandler;
import main.ru.yandex.practicum.http.handlers.HistroyHandler;
import main.ru.yandex.practicum.http.handlers.SubTaskHandler;
import main.ru.yandex.practicum.http.handlers.TaskHandler;
import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;


public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        TaskManager manager = new InMemoryTaskManager();
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Duration.class, new DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT),5);
        server.createContext("/task",new TaskHandler(manager));
        server.createContext("/subtask",new SubTaskHandler(manager));
        server.createContext("/epic",new EpicHandler(manager));
        server.createContext("/history",new HistroyHandler(manager));
//
        Epic epic = new Epic("epic","epic");
        manager.addEpic(epic);
//        String jsonString = "{\"epicId\": 4, \"name\": \"asfafs\", \"description\": \"AGag\", \"status\": \"NEW\"}";
//
//        SubTask subTask = gson.fromJson(jsonString,SubTask.class);
//        System.out.println(subTask);

//        SubTask subTask = new SubTask("asfafs","AGag",epic.getId());
//        SubTask subTask1 = new SubTask("asfafs","AGag",epic.getId());
//        manager.addSubTask(subTask);
//        manager.addSubTask(subTask1);

        System.out.println("asg");




        server.start();


    }
}
