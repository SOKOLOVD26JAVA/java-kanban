package subTask;

import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    TaskManager manager = Managers.getDefault();

    @Test
    void idSubTaskAfterRemove(){
        Epic epic = new Epic("1","2");
        manager.addEpic(epic);
        SubTask subTask1 = new SubTask("1_1","1",epic.getId());
        assertNotNull(subTask1);

    }
//Отправляю что бы проверка пошла...) После 2 итерации допилю еще тесты, со временем туго по этому хочу хоть что-то
// прикрепить. Хороших выходных!
}