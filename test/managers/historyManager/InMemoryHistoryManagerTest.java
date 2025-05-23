package managers.historyManager;
import main.ru.yandex.practicum.manager.HistoryManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager taskManager = Managers.getDefault();
    HistoryManager historyManager = Managers.getDefaultHistory();

    @Test
    void addTask() {
        Task task = null;
        historyManager.addTask(task);
        Task task2 = new Task("название1", "описание 1");
        historyManager.addTask(task2);
        assertEquals(historyManager.getHistory().getFirst(), task2, "Null объект добавился в таблицу.");
    }

    @Test
    void historyTest() {
        Task task1 = new Task("1","1");
        taskManager.addTask(task1);
        Task task2 = new Task("2","2");
        taskManager.addTask(task2);
        Task task3 = new Task("3","3");
        taskManager.addTask(task3);
        Epic epic1 = new Epic("4","4");
        taskManager.addEpic(epic1);
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.addTask(epic1);
        historyManager.addTask(epic1);
        historyManager.addTask(epic1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        assertEquals(historyManager.getHistory().size(),4,"Дубли не уходят");
    }
}