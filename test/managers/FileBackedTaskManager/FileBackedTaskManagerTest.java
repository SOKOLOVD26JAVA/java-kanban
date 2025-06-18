package managers.FileBackedTaskManager;
import main.ru.yandex.practicum.manager.FileBackedTaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManagerTest {

    @Test
    void addTaskToFile() {
        try {
            File tempFile = File.createTempFile("temp", ".csv");
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
            File tempFile = File.createTempFile("temp", ".csv");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Task task = new Task("Task", "TaskDesc");
            Task task2 = new Task("Task2", "TaskDesc");
            manager.addTask(task);
            manager.addTask(task2);
            FileBackedTaskManager.loadFromFile(tempFile);
            assertNotNull(manager.getTaskById(task.getId()),"Задача из файла не загрузилась.");
            assertNotNull(manager.getTaskById(task2.getId()),"Задача из файла не загрузилась.");

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
            assertNotNull(manager.getTaskById(task.getId()),"Задач из файла не загрузилась.");
            manager.removeTaskById(task.getId());
            FileBackedTaskManager.loadFromFile(tempFile);
            assertNull(manager.getTaskById(task.getId()),"Задач из файла не удалилась.");

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
            assertNull(manager.getTaskById(task.getId()),"Задачи из файла не удалились");
            assertNull(manager.getTaskById(task2.getId()),"Задачи из файла не удалились");
            assertNull(manager.getTaskById(task3.getId()),"Задачи из файла не удалились");
            assertNull(manager.getTaskById(task4.getId()),"Задачи из файла не удалились");

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
            assertNotEquals(manager.getTaskById(task.getId()).getStatus(),task.getStatus());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addSubTaskInEpicAfterLoadFromFile(){
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Epic epic = new Epic("epic","epic");
            manager.addEpic(epic);
            SubTask subTask = new SubTask("subtask","subtask",epic.getId());
            SubTask subTask2 = new SubTask("subtask","subtask",epic.getId());
            manager.addSubTask(subTask);
            manager.addSubTask(subTask2);
            FileBackedTaskManager.loadFromFile(tempFile);
            assertEquals(subTask.getId(),epic.getSubTasksID().getFirst());
            assertEquals(subTask2.getId(),epic.getSubTasksID().getLast());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addSubTaskIdInDifferentEpicsAfterLoadFromFile(){
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Epic epic = new Epic("epic","epic");
            manager.addEpic(epic);
            Epic epic1 = new Epic("epic","epic");
            manager.addEpic(epic1);
            SubTask subTask = new SubTask("subtask","subtask",epic.getId());
            manager.addSubTask(subTask);
            SubTask subTask2 = new SubTask("subtask","subtask",epic1.getId());
            manager.addSubTask(subTask2);
            FileBackedTaskManager.loadFromFile(tempFile);
            assertEquals(subTask.getId(),epic.getSubTasksID().getFirst());
            assertEquals(subTask2.getId(),epic1.getSubTasksID().getFirst());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void addTaskAfterLoadFromFile(){
        try {
            File tempFile = File.createTempFile("temp", ".csv");
            FileBackedTaskManager manager = new FileBackedTaskManager(tempFile.getAbsolutePath());
            Task task = new Task("Task", "TaskDesc");
            manager.addTask(task);
            FileBackedTaskManager manager1 = FileBackedTaskManager.loadFromFile(tempFile);
            Task task1 = new Task("Task1", "TaskDesc1");
            manager1.addTask(task1);
            assertEquals(manager.getTaskById(task.getId()).getId(),1);
            assertEquals(manager1.getTaskById(task1.getId()).getId(),2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


