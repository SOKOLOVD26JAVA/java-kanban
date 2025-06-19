package managers.AllManagersTest;
import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryTest {

    TaskManager manager;

    @BeforeEach
    void createManager(){
        manager = new InMemoryTaskManager();
    }

    @Test
    void getTask() {
        Task task = new Task("название1", "описание 1");
        manager.addTask(task);
        manager.getTaskById(task.getId());
        assertNotNull(manager.getHistory());
    }

    @Test
    void getEpic(){
        Epic epic = new Epic("epic","description");
        manager.addEpic(epic);
        manager.getEpicById(epic.getId());
        assertNotNull(manager.getHistory());
    }

    @Test
    void getSubTask(){
        Epic epic = new Epic("epic","description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("sub","description",epic.getId());
        manager.addSubTask(subTask);
        manager.getSubTaskById(subTask.getId());
        assertEquals(manager.getHistory().size(),1);
    }

    @Test
    void removeTask(){
        Task task = new Task("название1", "описание 1");
        manager.addTask(task);
        manager.getTaskById(task.getId());
        manager.removeTaskById(task.getId());
        assertEquals(manager.getHistory().size(),0);
    }

    @Test
    void removeEpic(){
        Epic epic = new Epic("epic","description");
        manager.addEpic(epic);
        manager.getEpicById(epic.getId());
        manager.removeEpicById(epic.getId());
        assertEquals(manager.getHistory().size(),0);
    }

    @Test
    void removeSubTask(){
        Epic epic = new Epic("epic","description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("sub","description",epic.getId());
        manager.addSubTask(subTask);
        manager.getSubTaskById(subTask.getId());
        manager.removeEpicById(epic.getId());
        assertEquals(manager.getHistory().size(),0);
    }




}