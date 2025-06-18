package subTask;

import main.ru.yandex.practicum.manager.InMemoryTaskManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    TaskManager manager = new InMemoryTaskManager();

    @Test
    void idSubTaskAfterRemoveTest(){
        Epic epic = new Epic("1","2");
        manager.addEpic(epic);
        SubTask subTask1 = new SubTask("1_1","1",epic.getId());
        assertNotNull(subTask1);

    }

    @Test
    void subTaskIdEqualsTest() {
        Epic epic1 = new Epic("Эпик 1", "описание");
        SubTask subTask1 = new SubTask(1, "Сабтаск 1", "описание", epic1.getId(), Status.NEW);
        SubTask subTask2 = new SubTask(1, "Сабтаск 2", "Описание", epic1.getId(), Status.NEW);
        assertEquals(subTask1, subTask2, "айди не равны");
    }
}