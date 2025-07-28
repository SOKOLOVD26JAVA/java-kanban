package managers.http.taskHandlerTest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.ru.yandex.practicum.http.HttpTaskServer;
import main.ru.yandex.practicum.http.handlers.TaskHandler;
import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.TaskManager;
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

public class TaskTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer server = new HttpTaskServer(manager,8080);
    Gson gson = HttpTaskServer.getGson();


    public TaskTest() throws IOException {
    }

    @BeforeEach
    public void setUp() throws IOException {
        manager.removeAllTasks();
        manager.removeAllSubTasks();
        manager.removeAllEpics();
        server.start();
        server.createContextTask();
    }

    @AfterEach
    public void shutDown() {
        server.stop();
    }

    @Test
    void postMethodTaskWithoutTime() throws IOException, InterruptedException {
        Task task = new Task("Name", "desc");
        String jsonTask = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertNotNull(manager.getTaskById(1));
    }

    @Test
    void postMethodTaskWithTime() throws IOException, InterruptedException {
        Task task = new Task("Name", "desc", LocalDateTime.now(), Duration.ofMinutes(15));
        String jsonTask = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertNotNull(manager.getTaskById(1).getTaskStart());
        assertNotNull(manager.getTaskById(1).getTaskDuration());
        assertNotNull(manager.getTaskById(1).getEndTime());
    }

    @Test
    void getMethod() throws IOException, InterruptedException {
//        1 Реквест - невалидный проверяю только ответ.
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task/1");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(404, response.statusCode());
        Task task = new Task("Name", "desc", LocalDateTime.now(), Duration.ofMinutes(15));
        manager.addTask(task);
//        Тут уже валидный, проверяю так же ответ.
        HttpResponse<String> response2 = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response2.statusCode());
//         Тут добавил задач и проверяю запрос когда у нас /task, т.е вернуть все задачи
        Task task2 = new Task("Name", "desc", LocalDateTime.now().minusDays(1), Duration.ofMinutes(15));
        Task task3 = new Task("Name", "desc", LocalDateTime.now().minusDays(3), Duration.ofMinutes(15));
        manager.addTask(task2);
        manager.addTask(task3);

        class TaskToken extends TypeToken<ArrayList<Task>> {}

        URI uri2 = URI.create("http://localhost:8080/task");
        HttpRequest request2 = HttpRequest.newBuilder().uri(uri2).GET().build();
        HttpResponse<String> response3 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        ArrayList<Task> tasks = gson.fromJson(response3.body(),new TaskToken().getType());
        assertEquals(tasks.get(2),task3);
        assertEquals(tasks.get(1),task2);
        assertEquals(tasks.get(0),task);
    }

    @Test
    void deleteMethod() throws IOException, InterruptedException {
//        Удаление задачи по айди.
        Task task = new Task("Name", "desc", LocalDateTime.now(), Duration.ofMinutes(15));
        manager.addTask(task);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task/1");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).DELETE().build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        assertEquals(200,response.statusCode());
        assertNull(manager.getTaskById(task.getId()));
//      Удаление всех задач P.S - Этого в ТЗ не было вроде, но мне что-то подсказывает что доложно работать именно так.
        Task task2 = new Task("Name", "desc", LocalDateTime.now(), Duration.ofMinutes(15));
        Task task3 = new Task("Name", "desc", LocalDateTime.now().minusDays(1), Duration.ofMinutes(15));
        Task task4 = new Task("Name", "desc", LocalDateTime.now().minusDays(3), Duration.ofMinutes(15));
        manager.addTask(task2);
        manager.addTask(task3);
        manager.addTask(task4);


        URI uri2 = URI.create("http://localhost:8080/task");
        HttpRequest request2 = HttpRequest.newBuilder().uri(uri2).DELETE().build();
        HttpResponse<String> response2 = client.send(request2,HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response2.statusCode());
        assertTrue(manager.printAllTasks().isEmpty());
    }

    @Test
    void postMethodWithCrossTask() throws IOException, InterruptedException {
//        Это невалидный - сначала задачу в менеджер, затем с таким же временем пытаюсь через POST отправить
        Task task1 = new Task("Name", "desc", LocalDateTime.now(), Duration.ofMinutes(15));
        manager.addTask(task1);
        Task task = new Task("Name", "desc", LocalDateTime.now(), Duration.ofMinutes(15));
        String jsonTask = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/task");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(406,response.statusCode());

        assertEquals(1,manager.printAllTasks().size());
    }
}
