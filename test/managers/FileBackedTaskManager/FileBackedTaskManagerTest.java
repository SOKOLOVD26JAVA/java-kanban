package managers.FileBackedTaskManager;
import main.ru.yandex.practicum.manager.FileBackedTaskManager;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {

    @Test
    void addTaskToFile() {
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Task task = new Task("Task", "TaskDesc");
            manager.addTask(task);
            FileReader read = new FileReader(tempFile.getAbsolutePath());
            BufferedReader br = new BufferedReader(read);
            if (br.ready()){
                br.readLine();
            }
            assertNotNull(br.readLine(),"Запись не удалась.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getTaskFromFile() {
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Task task = new Task("Task", "TaskDesc");
            Task task2 = new Task("Task2", "TaskDesc");
            manager.addTask(task);
            manager.addTask(task2);
            FileBackedTaskManager.loadFromFile(tempFile);
            assertNotNull(manager.getTasks().get(task.getId()),"Задача из файла не загрузилась.");
            assertNotNull(manager.getTasks().get(task2.getId()),"Задача из файла не загрузилась.");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    void removeTasksFromFile(){
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Task task = new Task("Task", "TaskDesc");
            manager.addTask(task);
            FileBackedTaskManager.loadFromFile(tempFile);
            assertNotNull(manager.getTasks().get(task.getId()),"Задач из файла не загрузилась.");
            manager.removeTaskById(task.getId());
            FileBackedTaskManager.loadFromFile(tempFile);
            assertNull(manager.getTasks().get(task.getId()),"Задач из файла не удалилась.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void removeAllTasks(){
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Task task = new Task("Task", "TaskDesc");
            Task task2 = new Task("Task2", "TaskDesc");
            Task task3 = new Task("Task3", "TaskDesc");
            Task task4 = new Task("Task4", "TaskDesc");
            manager.addTask(task);
            manager.addTask(task2);
            manager.addTask(task3);
            manager.addTask(task4);
            FileBackedTaskManager.loadFromFile(tempFile);
            assertNotNull(manager.printAllTasks(),"Задачи из файла не загрузились.");
            manager.removeAllTasks();
            FileBackedTaskManager.loadFromFile(tempFile);
            assertTrue(manager.getTasks().isEmpty(),"Задачи из файла не удалились");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void updateTask(){
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Task task = new Task("Task", "TaskDesc");
            Task task2 = new Task(1,"Task", "TaskDesc", Status.DONE);
            manager.addTask(task);
            manager.updateTask(task2);
            FileBackedTaskManager.loadFromFile(tempFile);
            assertNotEquals(manager.getTasks().get(task.getId()).getStatus(),task.getStatus());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


