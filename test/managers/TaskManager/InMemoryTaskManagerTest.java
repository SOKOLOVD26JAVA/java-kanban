//package managers.TaskManager;
//
//import main.ru.yandex.practicum.manager.InMemoryTaskManager;
//import main.ru.yandex.practicum.manager.Managers;
//import main.ru.yandex.practicum.model.Epic;
//import main.ru.yandex.practicum.model.SubTask;
//import main.ru.yandex.practicum.model.Task;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//
//import main.ru.yandex.practicum.manager.TaskManager;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//class InMemoryTaskManagerTest {
//    TaskManager taskManager = Managers.getDefault();
//    @AfterEach
//    void updateID(){
//        InMemoryTaskManager.id = 1;
//    }
//
//    @Test
//    void addNewTaskSubTaskAndEpicTest() {
//
//        Task task1 = new Task("Задача 1", "Описание1");
//        taskManager.addTask(task1);
//        Epic epic1 = new Epic("Имя эпика 1", "Описание эпика 1");
//        taskManager.addEpic(epic1);
//        SubTask subTask1 = new SubTask("Подзадача 1_1", "Описание 1_1", epic1.getId());
//        taskManager.addSubTask(subTask1);
//        assertNotNull(taskManager.getTaskById(task1.getId()), "Task не найден.");
//        assertNotNull(taskManager.getEpicById(epic1.getId()), "Epic не найден.");
//        assertNotNull(taskManager.getSubTaskById(subTask1.getId()), "SubTask не найден.");
//        final List<Task> tasks = taskManager.printAllTasks();
//        assertEquals(3, tasks.size(), "Неверное количество задач.");
//        final List<Epic> epics = taskManager.printAllEpics();
//        assertEquals(1, epics.size(), "Неверное количество задач.");
//        assertEquals(epic1, epics.getFirst(), "Задачи не совпадают.");
//        final List<SubTask> subTasks = taskManager.printAllSubTasks();
//        assertEquals(1, subTasks.size(), "Неверное количество задач.");
//        assertEquals(subTask1, subTasks.getFirst(), "Задачи не совпадают.");
//    }
//
//    @Test
//    void idConflictTest() {
//        TaskManager task = new InMemoryTaskManager();
//        Task task1 = new Task(1, "Задача 2", "описание");
//        Task task2 = new Task("Задача 3", "описание 3");
//        task.addTask(task1);
//        task.addTask(task2);
//        assertNotEquals(task1.getId(), task2.getId(), "Айди равны");
//    }
//
//    @Test
//    void removeEpicTest(){
//        TaskManager taskManager = new InMemoryTaskManager();
//        Epic epic1 = new Epic("Эпик 1","Описание");
//        Epic epic2 = new Epic("Эпик 1","Описание");
//        taskManager.addEpic(epic1);
//        taskManager.addEpic(epic2);
//        SubTask subTask1 = new SubTask("Сабтаск 1","Описание",epic1.getId());
//        taskManager.addSubTask(subTask1);
//        SubTask subTask2 = new SubTask("Сабтаск 2","Описание",epic1.getId());
//        SubTask subTask3 = new SubTask("Сабтаск 3","Описание",epic2.getId());
//        taskManager.addSubTask(subTask3);
//        taskManager.addSubTask(subTask2);
//        taskManager.removeEpicById(epic1.getId());
//        assertNull(taskManager.getSubTaskById(subTask1.getId()),"Сабтаск не удалился.");
//        assertNull(taskManager.getSubTaskById(subTask2.getId()),"Сабтаск не удалился.");
//        assertNotNull(taskManager.getSubTaskById(subTask3.getId()),"Этот сабтаск не удалялся");
//    }
//
//
//}