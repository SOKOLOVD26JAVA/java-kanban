//package task;
//
//
//import main.ru.yandex.practicum.manager.InMemoryTaskManager;
//import main.ru.yandex.practicum.manager.Managers;
//import main.ru.yandex.practicum.manager.TaskManager;
//import main.ru.yandex.practicum.model.Task;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//class TaskTest {
//    TaskManager manager = Managers.getDefault();
//    @AfterEach
//    void updateID(){
//        InMemoryTaskManager.id = 1;
//    }
//
//    @Test
//    void taskIdEqualsTest() {
//        Task task1 = new Task("Задача 1", "описание");
//        manager.addTask(task1);
//        Task task2 = new Task(1, "Задача 2", "описание");
//        assertEquals(task1.getId(), task2.getId(), "айди не равны");
//    }
//}