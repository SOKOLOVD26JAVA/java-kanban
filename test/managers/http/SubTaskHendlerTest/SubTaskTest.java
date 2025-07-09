package managers.http.SubTaskHendlerTest;

import com.google.gson.Gson;
import main.ru.yandex.practicum.http.HttpTaskServer;
import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
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

public class SubTaskTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer server = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public SubTaskTest() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        manager.removeAllTasks();
        manager.removeAllSubTasks();
        manager.removeAllEpics();
        server.start();
    }

    @AfterEach
    public void shutDown() {
        server.stop();
    }

    @Test
    void postMethodSubTaskWithoutTime() throws IOException, InterruptedException {
        Epic epic = new Epic("Name","desc");
        manager.addEpic(epic);
        SubTask task = new SubTask("Name", "desc",epic.getId());
        String jsonTask = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertNotNull(manager.getSubTaskById(2));
    }

    @Test
    void postSubTaskWithoutEpic() throws IOException, InterruptedException {
//        Невалидный - добавляю сабтаск без Эпика.
        SubTask task = new SubTask("Name", "desc",1);
        String jsonTask = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(404, response.statusCode());
        assertEquals(0,manager.printAllSubTasks().size());
    }

    @Test
    void postMethodSubTaskCross() throws IOException, InterruptedException {
//        Невалидный- добавляю сабтаск в менеджер и с таким-же временем через POST
        Epic epic = new Epic("Name","desc");
        manager.addEpic(epic);
        SubTask task = new SubTask("Name", "desc",LocalDateTime.now(),Duration.ofMinutes(5),epic.getId());
        manager.addSubTask(task);
        SubTask task2 = new SubTask("Name", "desc",LocalDateTime.now(),Duration.ofMinutes(5),epic.getId());
        String jsonTask = gson.toJson(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(406,response.statusCode());

        assertEquals(1,manager.printAllSubTasks().size());

    }

    @Test
    void updateSubTaskWithCross() throws IOException, InterruptedException {
//        Не валидный, все как и в предыдущем только обновляю задачу, потом по имени проверяю что обновление не произошло
        Epic epic = new Epic("Name","desc");
        manager.addEpic(epic);
        SubTask task = new SubTask("Name1", "desc",LocalDateTime.now(),Duration.ofMinutes(5),epic.getId());
        manager.addSubTask(task);
        SubTask task2 = new SubTask("Name2", "desc",LocalDateTime.now(),Duration.ofMinutes(5),epic.getId());
        String jsonTask = gson.toJson(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(406,response.statusCode());

        assertEquals(manager.getSubTaskById(2).getName(),"Name1");
    }


    @Test
    void updateSubTask() throws IOException, InterruptedException {
        Epic epic = new Epic("Name","desc");
        manager.addEpic(epic);
        SubTask task = new SubTask("Name1", "desc",LocalDateTime.now(),Duration.ofMinutes(5),epic.getId());
        manager.addSubTask(task);
        SubTask task2 = new SubTask(2,"Name2", Status.NEW, "desc",LocalDateTime.now().minusDays(5),Duration.ofMinutes(5),epic.getId());
        String jsonTask = gson.toJson(task2);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/subtask");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

        assertEquals(manager.getSubTaskById(2).getName(),"Name2");
    }
}
