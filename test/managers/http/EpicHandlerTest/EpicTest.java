package managers.http.EpicHandlerTest;

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


public class EpicTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer server = new HttpTaskServer(manager);
    Gson gson = HttpTaskServer.getGson();

    public EpicTest() throws IOException {
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
    void postMethodForEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Name","desc");
        String jsonTask = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertNotNull(manager.getEpicById(1));
    }

    @Test
    void getSubTaskByEpicId() throws IOException, InterruptedException {
        Epic epic = new Epic("Name","desc");
        manager.addEpic(epic);
        SubTask task = new SubTask("Name", "desc",epic.getId());
        manager.addSubTask(task);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic/1/subtasks");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        class TaskToken extends TypeToken<ArrayList<SubTask>> {}

        ArrayList<SubTask> subTasks = gson.fromJson(response.body(),new TaskToken().getType());

        assertEquals(200,response.statusCode());
        assertEquals(subTasks.getFirst(),task);
    }

    @Test
    void updateEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("Name","desc");
        manager.addEpic(epic);
        Epic epic1 = new Epic(1,"Name2","desc");
        String jsonTask = gson.toJson(epic1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());
        assertEquals(manager.getEpicById(1).getName(),"Name2");
    }

    @Test
    void updateEpicWithTime() throws IOException, InterruptedException {
//        Я тут намудрил, ну короче..) Сначала добавляю эпик в менеджера.
        Epic epic = new Epic("Name","desc");
        manager.addEpic(epic);
        SubTask task = new SubTask("Name", "desc",epic.getId());
        manager.addSubTask(task);
//        Теперь через POST отправляю эпик.
        Epic epic1 = new Epic("Name2","desc");
        String jsonTask = gson.toJson(epic1);
        HttpClient client = HttpClient.newHttpClient();
        URI uri = URI.create("http://localhost:8080/epic");
        HttpRequest request = HttpRequest.newBuilder().uri(uri).POST(HttpRequest.BodyPublishers.ofString(jsonTask)).build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response.statusCode());

//      И сабтаск
        SubTask task1 = new SubTask("Name", "desc",LocalDateTime.now(), Duration.ofMinutes(15),3);
        String jsonTask1 = gson.toJson(task1);
        URI uri1 = URI.create("http://localhost:8080/subtask");
        HttpRequest request1 = HttpRequest.newBuilder().uri(uri1).POST(HttpRequest.BodyPublishers.ofString(jsonTask1)).build();
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response1.statusCode());
//      Тут получаю обратно этот эпик в который отправлял сабтаск
        URI uri2 = URI.create("http://localhost:8080/epic/3");
        HttpRequest request2 = HttpRequest.newBuilder().uri(uri2).GET().build();
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
//      Ставлю ему айди = 0, то бы обновить
        Epic newEpic = gson.fromJson(response2.body(),Epic.class);
        newEpic.setId(0);

        String jsonTask2 = gson.toJson(newEpic);
        URI uri3 = URI.create("http://localhost:8080/epic");
        HttpRequest request3 = HttpRequest.newBuilder().uri(uri3).POST(HttpRequest.BodyPublishers.ofString(jsonTask2)).build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        assertEquals(200,response3.statusCode());
//      По имени первого эпика проверяю обновление.
        assertEquals(epic1.getName(),"Name2");









    }


}
