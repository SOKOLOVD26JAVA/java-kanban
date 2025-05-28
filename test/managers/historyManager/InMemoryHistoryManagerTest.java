package managers.historyManager;
import main.ru.yandex.practicum.manager.HistoryManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
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
    void historyTaskTest() {
        Task task1 = new Task("Задача 1","описание 1");
        taskManager.addTask(task1);
        Task task2 = new Task("Задача 2","описание 2");
        taskManager.addTask(task2);
        Task task3 = new Task("Задача 3","описание 3");
        taskManager.addTask(task3);
        historyManager.addTask(task1);
        historyManager.addTask(task2);
        historyManager.addTask(task3);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        assertEquals(historyManager.getHistory().size(),3,"Дубли не уходят.");
    }

    @Test
    void historyEpicAndSubtaskTest(){
        Epic epic1 = new Epic("Эпик 1","Описание");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Сабтаск 1","Описание",epic1.getId());
        taskManager.addSubTask(subTask1);
        SubTask subTask2 = new SubTask("Сабтаск 2","Описание",epic1.getId());
        taskManager.addSubTask(subTask2);
        Epic epic2 = new Epic("Эпик 2","Описание");
        taskManager.addEpic(epic2);
        historyManager.addTask(epic1);
        historyManager.addTask(subTask1);
        historyManager.addTask(subTask2);
        historyManager.addTask(epic2);
        assertEquals(historyManager.getHistory().size(),4,"Что то не добавилось в историю.");

    }
    @Test
    void removeTask(){
        Task task1 = new Task("Задача 1","описание 1");
        taskManager.addTask(task1);
        historyManager.addTask(task1);
        assertNotNull(historyManager.getHistory(),"таск не добавлен");
        historyManager.remove(task1.getId());
        assertEquals(historyManager.getHistory().size(),0,"Таск не удалился");
    }

    @Test
    void removeEpicAndSubtaskTest(){
        Epic epic1 = new Epic("Эпик 1","Описание");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Сабтаск 1","Описание",epic1.getId());
        taskManager.addSubTask(subTask1);
        SubTask subTask2 = new SubTask("Сабтаск 2","Описание",epic1.getId());
        taskManager.addSubTask(subTask2);
        historyManager.addTask(epic1);
        historyManager.addTask(subTask1);
        assertNotNull(historyManager.getHistory(),"Сабтаск или эпик не добавлен.");
        historyManager.remove(epic1.getId());
        historyManager.remove(subTask1.getId());
        assertEquals(historyManager.getHistory().size(),0,"Сабтаск или эпик не удалился");

    }
}