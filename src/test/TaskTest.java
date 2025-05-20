package test;

import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    TaskManager taskManager = Managers.getDefault();


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
        SubTask subTask1 = new SubTask(1,"Сабтаск 1","описание",epic1.getId(), Status.NEW);
        SubTask subTask2 = new SubTask(1,"Сабтаск 2","Описание",epic1.getId(), Status.NEW);
        assertEquals(subTask1,subTask2,"айди не равны");
    }
}