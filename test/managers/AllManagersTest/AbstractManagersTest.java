package managers.AllManagersTest;

import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import main.ru.yandex.practicum.manager.TaskManager;

import java.time.Duration;
import java.time.LocalDateTime;

public abstract class AbstractManagersTest<T extends TaskManager> {

    protected abstract T getManager();

    @Test
    void addTaskTest() {
        T manager = getManager();
        Task task = new Task("name", "description");
        manager.addTask(task);
        assertNotNull(manager.getTaskById(task.getId()));
    }

    @Test
    void addEpicAndSubtaskTest() {
        T manager = getManager();
        Epic epic = new Epic("name", "description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1", "description1", epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("name2", "description2", epic.getId());
        manager.addSubTask(subTask2);
        assertNotNull(manager.getEpicById(epic.getId()));
        assertNotNull(manager.getSubTaskById(subTask.getId()));
        assertNotNull(manager.getSubTaskById(subTask2.getId()));
    }

    @Test
    void removeTaskSubtaskAndEpic() {
        T manager = getManager();
        Task task = new Task("name", "description");
        manager.addTask(task);
        Epic epic = new Epic("name", "description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1", "description1", epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("name2", "description2", epic.getId());
        manager.addSubTask(subTask2);
        manager.removeTaskById(task.getId());
        manager.removeEpicById(epic.getId());
        assertNull(manager.getTaskById(task.getId()), "Задача не удалилась");
        assertNull(manager.getEpicById(epic.getId()), "Задача не удалилась");
        assertNull(manager.getSubTaskById(subTask.getId()));
        assertNull(manager.getSubTaskById(subTask2.getId()));
    }

    @Test
    void removeSubTask() {
        T manager = getManager();
        Epic epic = new Epic("name", "description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1", "description1", epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("name2", "description2", epic.getId());
        manager.addSubTask(subTask2);
        manager.removeAllEpics();
        assertNull(manager.getTaskById(subTask.getId()));
        assertNull(manager.getTaskById(subTask2.getId()));
    }

    @Test
    void updateAllTypeTask() {
        T manager = getManager();
        Task task = new Task("name", "description");
        manager.addTask(task);
        Task task2 = new Task(1, "name", "description", Status.DONE);
        manager.updateTask(task2);
        assertEquals(manager.getTaskById(1).getStatus(), Status.DONE, "Задача не обновилась");
        Epic epic = new Epic("name", "description");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("name1", "description1", epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask(3, "name2", "description2", epic.getId(), Status.DONE);
        manager.updateSubTask(subTask2);
        assertEquals(manager.getEpicById(epic.getId()).getStatus(), Status.DONE);
        assertEquals(manager.getSubTaskById(3).getStatus(), Status.DONE);
    }

    @Test
    void addAllTypeTaskWithOutTime() {
        T manager = getManager();
        Task task = new Task("task", "desc");
        manager.addTask(task);
        Epic epic = new Epic("epic", "desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("subtask", "desc", epic.getId());
        manager.addSubTask(subTask);
        assertEquals(0, manager.getPrioritizedTasks().size());
    }


    @Test
    void addAllTypeTaskInPrioritized() {
        T manager = getManager();
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task);
        Epic epic = new Epic("epic", "desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("subtask", "desc", LocalDateTime.now().plusMinutes(2),
                Duration.ofMinutes(2), epic.getId());
        manager.addSubTask(subTask);
        assertEquals(2, manager.getPrioritizedTasks().size());
    }

    @Test
    void taskCross() {
        T manager = getManager();
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task);
        Task task1 = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task1);
        assertEquals(1, manager.getPrioritizedTasks().size());
    }

    @Test
    void updateEpicTime() {
        T manager = getManager();
        Epic epic = new Epic("epic", "desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("subtask", "desc", LocalDateTime.now(),
                Duration.ofMinutes(2), epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask2 = new SubTask("subtask", "desc", LocalDateTime.now().plusMinutes(2),
                Duration.ofMinutes(2), epic.getId());
        manager.addSubTask(subTask2);
        LocalDateTime endEpicTime = epic.getEndTime();
        manager.removeSubTaskById(subTask2.getId());
        assertNotEquals(epic.getEndTime(), endEpicTime);
    }

    @Test
    void addTaskBeforeTaskStart(){
        T manager = getManager();
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task);
        Task task1 = new Task("task", "desc", LocalDateTime.now().minusMinutes(10), Duration.ofMinutes(1));
        manager.addTask(task1);
        assertEquals(task1.getId(),manager.getPrioritizedTasks().getFirst().getId());
    }

    @Test
    void addTaskAfterTaskStart(){
        T manager = getManager();
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(1));
        manager.addTask(task);
        Task task1 = new Task("task", "desc", LocalDateTime.now().plusMinutes(10), Duration.ofMinutes(1));
        manager.addTask(task1);
        assertEquals(task.getId(),manager.getPrioritizedTasks().getFirst().getId());
    }

    @Test
    void updateCrossTask(){
        T manager = getManager();
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(5));
        manager.addTask(task);
        Task task1 = new Task("updated task", "desc", LocalDateTime.now(), Duration.ofMinutes(10));
        manager.updateTask(task1);
        assertEquals(manager.getPrioritizedTasks().getFirst().getName(),"task");
        assertEquals(manager.getPrioritizedTasks().size(),1);
    }

    @Test
    void updateCrossSubTask(){
        T manager = getManager();
        Epic epic = new Epic("epic","desc");
        manager.addEpic(epic);
        SubTask subTask = new SubTask("subtask", "desc", LocalDateTime.now(),
                Duration.ofMinutes(2), epic.getId());
        manager.addSubTask(subTask);
        SubTask subTask1 = new SubTask("updated subtask", "desc", LocalDateTime.now(),
                Duration.ofMinutes(10), epic.getId());
        manager.updateSubTask(subTask1);
        assertEquals(manager.getPrioritizedTasks().getFirst().getName(),"subtask");
        assertEquals(manager.getPrioritizedTasks().size(),1);
    }

    @Test
    void crossBeforeTaskStart(){
        T manager = getManager();
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(5));
        manager.addTask(task);
        Task task1 = new Task(" task", "desc", LocalDateTime.now().minusMinutes(5),
                Duration.ofMinutes(5).plusSeconds(1));
        manager.addTask(task1);
        assertEquals(manager.getPrioritizedTasks().size(),1);
    }

    @Test
    void crossAfterTaskStart(){
        T manager = getManager();
        Task task = new Task("task", "desc", LocalDateTime.now(), Duration.ofMinutes(5));
        manager.addTask(task);
        Task task1 = new Task(" task", "desc", LocalDateTime.now().plusMinutes(5).minusSeconds(1),
                Duration.ofMinutes(5));
        manager.addTask(task1);
        assertEquals(manager.getPrioritizedTasks().size(),1);
    }

}
