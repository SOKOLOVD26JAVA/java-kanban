package ru.yandex.practicum.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.manager.HistoryManager;
import ru.yandex.practicum.manager.Managers;
import ru.yandex.practicum.manager.TaskManager;
import ru.yandex.practicum.model.Epic;
import ru.yandex.practicum.model.Status;
import ru.yandex.practicum.model.SubTask;
import ru.yandex.practicum.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class InMemoryTaskManagerTest {
//    Как я понимаю это можно принять за "убедитесь, что утилитарный класс всегда возвращает проинициализированные и готовые к работе экземпляры менеджеров;"
    TaskManager taskManager = Managers.getDefault();
   HistoryManager historyManager = Managers.getDefaultHistory();
    @Test
    void addNewTaskSubTaskAndEpicTest() {
        Task task1 = new Task("Задача 1","Описание1");
        taskManager.addTask(task1);
        Epic epic1 = new Epic("Имя эпика 1","Описание эпика 1");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1_1","Описание 1_1", epic1.getId());
        taskManager.addSubTask(subTask1);
        assertNotNull(taskManager.getTaskById(task1.getId()), "Task не найден.");
        assertNotNull(taskManager.getEpicById(epic1.getId()), "Epic не найден.");
        assertNotNull(taskManager.getSubTaskById(subTask1.getId()), "SubTask не найден.");
//      Что касается - "проверьте, что объект Epic нельзя добавить в самого себя в виде подзадачи;
//      проверьте, что объект Subtask нельзя сделать своим же эпиком". Невозможность вызвать taskManager.addSubTask(subTask1)
//        и taskManager.addEpic(epic1) так же можно принять за тест:D
       final List<Task> tasks = taskManager.printAllTasks();
       assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task1, tasks.getFirst(), "Задачи не совпадают.");
        final List<Epic> epics = taskManager.printAllEpics();
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic1, epics.getFirst(), "Задачи не совпадают.");
        final List<SubTask> subTasks = taskManager.printAllSubTasks();
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask1, subTasks.getFirst(), "Задачи не совпадают.");
    }
    @Test
    void idConflictTest(){
        Task task1 = new Task(1,"Задача 2","описание");
        Task task2 = new Task("Задача 3","описание 3");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertNotEquals(task1.getId(),task2.getId(),"Айди равны");
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