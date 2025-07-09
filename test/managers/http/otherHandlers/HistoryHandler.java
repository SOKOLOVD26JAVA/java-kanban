package managers.http.otherHandlers;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.ru.yandex.practicum.http.HttpTaskServer;
import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class HistoryHandler {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer server = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public HistoryHandler() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        manager.removeAllTasks();
        manager.removeAllSubTasks();
        manager.removeAllEpics();
        server.start();
        manager.getHistory().clear();
    }

    @AfterEach
    public void shutDown() {
        server.stop();
    }

    @Test
    void returnHistory() throws IOException, InterruptedException {
        Task task = new Task("Name","Description");
        manager.addTask(task);
        Epic epic = new Epic("Name","Description");
        manager.addEpic(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task/1");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        URI uri1 = URI.create("http://localhost:8080/epic/2");
        HttpRequest request1 = HttpRequest.newBuilder().uri(uri1).GET().build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response1.statusCode());

        URI uri2 = URI.create("http://localhost:8080/history");
        HttpRequest request2 = HttpRequest.newBuilder().uri(uri2).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        class TaskToken extends TypeToken<ArrayList<Task>> {}

        assertEquals(200,response2.statusCode());

        ArrayList<Task> history = gson.fromJson(response2.body(),new TaskToken().getType());

        assertEquals(history.get(0).getId(),task.getId());
        assertEquals(history.get(1).getId(),epic.getId());
    }

    @Test
    void anotherMethodForHistory() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/history");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(500,response.statusCode());
    }
}
