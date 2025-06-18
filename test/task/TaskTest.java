package task;

import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TaskTest {
    TaskManager taskManager = new InMemoryTaskManager();


    @Test
    void taskIdEqualsTest() {
        Task task1 = new Task("Задача 1", "описание");
        taskManager.addTask(task1);
        Task task2 = new Task(1, "Задача 2", "описание");
        assertEquals(task1, task2, "айди не равны");
    }
}