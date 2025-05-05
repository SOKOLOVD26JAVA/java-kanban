package test;

import main.ru.yandex.practicum.manager.HistoryManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class InMemoryHistoryManagerTest {
TaskManager taskManager = Managers.getDefault();
HistoryManager historyManager = Managers.getDefaultHistory();
    @Test
    void addTask() {
        Task task = null;
        historyManager.addTask(task);
        Task task2 = new Task("название1","описание1");
        historyManager.addTask(task2);
        assertEquals(historyManager.getHistory().getFirst(),task2,"Null объект добавился в таблицу.");
    }

    @Test
    void historyTest(){
        Task task1 = new Task(1,"Задача 2","описание");
        taskManager.addTask(task1);
        Epic epic1 = new Epic("Имя эпика 1","Описание эпика 1");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1_1","Описание 1_1", epic1.getId());
        taskManager.addSubTask(subTask1);
        List<Task> history = historyManager.getHistory();
        assertEquals(0,history.size(),"Список не пуст.");
        historyManager.addTask(task1);
        historyManager.addTask(epic1);
        historyManager.addTask(subTask1);
        List<Task> updateHistory = historyManager.getHistory();
        assertEquals(3,updateHistory.size(),"Список пуст.");
        assertEquals(task1,updateHistory.get(0),"Задача изменена");
        assertEquals(epic1,updateHistory.get(1),"Задача изменена");
        assertEquals(subTask1,updateHistory.get(2),"Задача изменена");
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(task1);
        historyManager.addTask(epic1);
        List<Task> newUpdateHistory = historyManager.getHistory();
        assertEquals(newUpdateHistory.getFirst(),epic1,"При добавлении более 10 задач последний элемент " +
                "не удаляется.");

    }
}