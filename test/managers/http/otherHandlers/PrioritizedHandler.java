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
import java.util.TreeSet;

public class PrioritizedHandler {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer server = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public PrioritizedHandler() throws IOException {
    }


    @BeforeEach
    public void setUp() throws IOException {
        manager.removeAllTasks();
        manager.removeAllSubTasks();
        manager.removeAllEpics();
        server.start();
        manager.getHistory().clear();
        manager.getPrioritizedTasks().clear();
    }

    @AfterEach
    public void shutDown() {
        server.stop();
    }

    @Test
    void returnPrioritized() throws IOException, InterruptedException {
        Task task = new Task("Name","desc",LocalDateTime.now(),Duration.ofMinutes(5));
        manager.addTask(task);
        Task task1 = new Task("Name", "desc",LocalDateTime.now().minusDays(2),Duration.ofMinutes(5));
        manager.addTask(task1);

        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        class TaskToken extends TypeToken<ArrayList<Task>> {}

        ArrayList<Task> sortedTasks = gson.fromJson(response.body(),new TaskToken());

        assertEquals(task1.getId(),sortedTasks.getFirst().getId());
        assertEquals(task.getId(),sortedTasks.getLast().getId());

    }

    @Test
    void anotherMethodForPrioritized() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/prioritized");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(500,response.statusCode());
    }

}
