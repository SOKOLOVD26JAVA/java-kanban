package managers.AllManagersTest;

import main.ru.yandex.practicum.manager.FileBackedTaskManager;
import main.ru.yandex.practicum.manager.ManagerSaveException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTest extends AbstractManagersTest<FileBackedTaskManager> {
    private Path tempFile;
    private FileBackedTaskManager manager;

    @BeforeEach
    void createFile() throws IOException {
        tempFile = Files.createTempFile("task","csv");
         manager = new FileBackedTaskManager(tempFile.toString());
    }

    @AfterEach
    void deleteFile() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Override
    protected FileBackedTaskManager getManager() {
        return manager;
    }

    @Test
    void addTaskToFile() {
        Task task = new Task("Task", "TaskDesc");
        manager.addTask(task);
        try (Reader wr = new FileReader(String.valueOf(tempFile))) {
            BufferedReader br = new BufferedReader(wr);
            br.readLine();
            assertNotNull(br.readLine());
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка");
        }
    }

    @Test
    void getTaskFromFile() {
        Task task = new Task("Task", "TaskDesc");
        Task task2 = new Task("Task2", "TaskDesc");
        manager.addTask(task);
        manager.addTask(task2);
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        assertNotNull(manager.getTaskById(task.getId()));
        assertNotNull(manager.getTaskById(task2.getId()));
    }

    @Test
    void removeTasksFromFile() {
        Task task = new Task("Task", "TaskDesc");
        manager.addTask(task);
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        assertNotNull(manager.getTaskById(task.getId()));
        manager.removeTaskById(task.getId());
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        assertNull(manager.getTaskById(task.getId()));
    }

    @Test
    void removeAllTasks() {
        Task task1 = new Task("Task1", "Desc");
        Task task2 = new Task("Task2", "Desc");
        manager.addTask(task1);
        manager.addTask(task2);
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        assertNotNull(manager.printAllTasks());
        manager.removeAllTasks();
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        assertTrue(manager.printAllTasks().isEmpty());
    }

    @Test
    void updateTask() {
        Task task = new Task("Task", "Desc");
        manager.addTask(task);
        Task updatedTask = new Task(task.getId(), "Updated", "Desc", Status.DONE);
        manager.updateTask(updatedTask);
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        assertEquals(Status.DONE, manager.getTaskById(task.getId()).getStatus());
    }

    @Test
    void addSubTaskInEpicAfterLoadFromFile() {
        Epic epic = new Epic("Epic", "Desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("Sub", "Desc", epic.getId());
        manager.addSubTask(subTask);
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        Epic epic1 = manager.getEpicById(epic.getId());
        assertTrue(epic1.getSubTasksID().contains(subTask.getId()));
    }

    @Test
    void addSubTaskIdInDifferentEpicsAfterLoadFromFile() {
        Epic epic1 = new Epic("Epic1", "Desc");
        Epic epic2 = new Epic("Epic2", "Desc");
        manager.addEpic(epic1);
        manager.addEpic(epic2);
        SubTask sub1 = new SubTask("Sub1", "Desc", epic1.getId());
        SubTask sub2 = new SubTask("Sub2", "Desc", epic2.getId());
        manager.addSubTask(sub1);
        manager.addSubTask(sub2);
        manager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        Epic epic3 = manager.getEpicById(epic1.getId());
        Epic epic4 = manager.getEpicById(epic2.getId());
        assertTrue(epic3.getSubTasksID().contains(sub1.getId()));
        assertTrue(epic4.getSubTasksID().contains(sub2.getId()));
    }

    @Test
    void addTaskAfterLoadFromFile() {
        Task task = new Task("Task", "Desc");
        manager.addTask(task);
        FileBackedTaskManager newManager = FileBackedTaskManager.loadFromFile(tempFile.toFile());
        Task newTask = new Task("NewTask", "Desc");
        newManager.addTask(newTask);
        assertEquals(task.getId(), newTask.getId());
        assertNotNull(newManager.getTaskById(newTask.getId()));
    }
}


