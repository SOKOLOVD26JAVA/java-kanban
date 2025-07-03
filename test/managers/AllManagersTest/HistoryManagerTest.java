package managers.AllManagersTest;

import main.ru.yandex.practicum.manager.HistoryManager;
import main.ru.yandex.practicum.manager.InMemoryHistoryManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryManagerTest {

    HistoryManager manager;

    @BeforeEach
    void createManager() {
        manager = Managers.getDefaultHistory();
    }

    @Test
    void addTask() {
        Task task = new Task("task", "desc");
        manager.addTask(task);
        assertNotNull(manager.getHistory());
    }

    @Test
    void addEpicAndSubTask() {
        Epic epic = new Epic("epic", "desc");
        manager.addTask(epic);
        SubTask subTask = new SubTask("subtask", "desc", epic.getId());
        manager.addTask(subTask);
        assertNotNull(manager.getHistory());
    }

    @Test
    void removeTask() {
        Task task = new Task("task", "desc");
        manager.addTask(task);
        assertNotNull(manager.getHistory());
        manager.remove(task.getId());
        assertEquals(0, manager.getHistory().size());
    }

    @Test
    void removeEpicAndSubtask() {
        Epic epic = new Epic("epic", "desc");
        manager.addTask(epic);
        SubTask subTask = new SubTask("subtask", "desc", epic.getId());
        manager.addTask(subTask);
        SubTask subTask1 = new SubTask("subtask", "desc", epic.getId());
        manager.addTask(subTask1);
        assertNotNull(manager.getHistory());
        manager.remove(epic.getId());
        manager.remove(subTask.getId());
        manager.remove(subTask1.getId());
        assertEquals(0, manager.getHistory().size());
    }

    @Test
    void taskDuplicate() {
        Task task = new Task("task", "desc");
        manager.addTask(task);
        manager.addTask(task);
        assertEquals(1, manager.getHistory().size());
    }


}
