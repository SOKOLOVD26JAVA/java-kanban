package timeTest;

import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class timeTest {

    InMemoryTaskManager manager;

    @BeforeEach
    void createManager() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void addAllTypeTaskWithOutTime() {
        Task task = new Task("task", "desc");
        manager.addTask(task);
        Epic epic = new Epic("epic", "desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("subtask", "desc", epic.getId());
        manager.addSubTask(subTask);
        assertEquals(0, manager.getPrioritizedTasks().size());
    }


    @Test
    void addAllTypeTaskInPrioritized() {
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task);
        Epic epic = new Epic("epic", "desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("subtask", "desc", LocalDateTime.now().plusMinutes(2),
                Duration.ofMinutes(2), epic.getId());
        manager.addSubTask(subTask);
        assertEquals(2, manager.getPrioritizedTasks().size());
    }

    @Test
    void taskCross() {
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task);
        Task task1 = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task1);
        assertEquals(1, manager.getPrioritizedTasks().size());
    }

    @Test
    void updateEpicTime() {
        Epic epic = new Epic("epic", "desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("subtask", "desc", LocalDateTime.now(),
                Duration.ofMinutes(2), epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("subtask", "desc", LocalDateTime.now().plusMinutes(2),
                Duration.ofMinutes(2), epic.getId());
        manager.addSubTask(subTask2);
        LocalDateTime endEpicTime = epic.getEndTime();
        manager.removeSubTaskById(subTask2.getId());
        assertNotEquals(epic.getEndTime(), endEpicTime);
    }


}
