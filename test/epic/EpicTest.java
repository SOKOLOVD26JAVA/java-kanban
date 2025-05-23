package epic;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EpicTest {
    TaskManager manager = Managers.getDefault();

    @Test
    void subTaskIdInEpicAfterRemove(){
        Epic epic1 = new Epic("1","2");
        manager.addEpic(epic1);
        SubTask subTask1 = new SubTask("1.1","1", epic1.getId());
        manager.addSubTask(subTask1);
        SubTask subTask2 = new SubTask("1.2","2", epic1.getId());
        manager.addSubTask(subTask2);
        SubTask subTask3 = new SubTask("1.3","3", epic1.getId());
        manager.addSubTask(subTask3);
        manager.removeSubTaskById(subTask1.getId());
        assertEquals(epic1.getSubTasksID().size(),2,"После удаления в эпике остался айди удаленного " +
                "SubTask");
    }

}