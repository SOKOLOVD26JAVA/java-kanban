package test;

import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;
import main.ru.yandex.practicum.manager.HistoryManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;

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
    void idConflictTest() {
//        Тут я имел ввиду то ,что даже если мы вручную зададем айди у нас в любом случае айди перезаписывается, типо у
//        нас не может быть двух задач с двумя одинаковыми айди.
        Task task1 = new Task(1,"Задача 2","описание");
        Task task2 = new Task("Задача 3","описание 3");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        assertNotEquals(task1.getId(),task2.getId(),"Айди равны");
    }

    @Test
    void taskIdEquals() {
        Task task1 = new Task("Задача 1","описание");
        taskManager.addTask(task1);
        Task task2 = new Task(1,"Задача 2","описание");
        assertEquals(task1,task2,"айди не равны");
    }

    @Test
    void epicIdEquals() {
        Epic epic1 = new Epic("Эпик 1","описание");
        taskManager.addEpic(epic1);
        Epic epic2 = new Epic(1,"Эпик 2","описание");
        assertEquals(epic1,epic2,"айди не равны");
    }

    @Test
    void subTaskIdEquals() {
        Epic epic1 = new Epic("Эпик 1","описание");
        SubTask subTask1 = new SubTask(1,"Сабтаск 1","описание",epic1.getId(),Status.NEW);
        SubTask subTask2 = new SubTask(1,"Сабтаск 2","Описание",epic1.getId(), Status.NEW);
        assertEquals(subTask1,subTask2,"айди не равны");
    }
}