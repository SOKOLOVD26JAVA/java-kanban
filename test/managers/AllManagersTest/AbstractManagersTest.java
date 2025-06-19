package managers.AllManagersTest;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import main.ru.yandex.practicum.manager.TaskManager;

public abstract class AbstractManagersTest<T extends TaskManager> {

    protected abstract T getManager();

    @Test
    void addTaskTest(){
        T manager = getManager();
        Task task = new Task("name","description");
        manager.addTask(task);
        assertNotNull(manager.getTaskById(task.getId()));
    }

    @Test
    void addEpicAndSubtaskTest(){
        T manager = getManager();
        Epic epic = new Epic("name","description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1","description1",epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("name2","description2",epic.getId());
        manager.addSubTask(subTask2);
        assertNotNull(manager.getEpicById(epic.getId()));
        assertNotNull(manager.getSubTaskById(subTask.getId()));
        assertNotNull(manager.getSubTaskById(subTask2.getId()));
    }

    @Test
    void removeTaskSubtaskAndEpic(){
        T manager = getManager();
        Task task = new Task("name","description");
        manager.addTask(task);
        Epic epic = new Epic("name","description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1","description1",epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("name2","description2",epic.getId());
        manager.addSubTask(subTask2);
        manager.removeTaskById(task.getId());
        manager.removeEpicById(epic.getId());
        assertNull(manager.getTaskById(task.getId()),"Задача не удалилась");
        assertNull(manager.getEpicById(epic.getId()),"Задача не удалилась");
        assertNull(manager.getSubTaskById(subTask.getId()));
        assertNull(manager.getSubTaskById(subTask2.getId()));
    }

    @Test
    void removeSubTask(){
        T manager = getManager();
        Epic epic = new Epic("name","description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1","description1",epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("name2","description2",epic.getId());
        manager.addSubTask(subTask2);
        manager.removeAllEpics();
        assertNull(manager.getTaskById(subTask.getId()));
        assertNull(manager.getTaskById(subTask2.getId()));
    }

    @Test
    void updateAllTypeTask(){
        T manager = getManager();
        Task task = new Task("name","description");
        manager.addTask(task);
        Task task2 = new Task(1,"name","description", Status.DONE);
        manager.updateTask(task2);
        assertEquals(manager.getTaskById(1).getStatus(),Status.DONE,"Задача не обновилась");
        Epic epic = new Epic("name","description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1","description1",epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask(3,"name2","description2",epic.getId(),Status.DONE);
        manager.updateSubTask(subTask2);
        assertEquals(manager.getEpicById(epic.getId()).getStatus(),Status.DONE);
        assertEquals(manager.getSubTaskById(3).getStatus(),Status.DONE);
        System.out.println("");
    }

}
