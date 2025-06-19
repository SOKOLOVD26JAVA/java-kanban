package managers.ManagersNotNullTests;

import main.ru.yandex.practicum.manager.FileBackedTaskManager;
import main.ru.yandex.practicum.manager.HistoryManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ManagersNotNullTest {

    @Test
    void getDefaultTest() {
        TaskManager taskManager = Managers.getDefault();
        assertNotNull(taskManager);
    }

    @Test
    void getDefaultHistoryTest() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager);
    }

    @Test
    void notNullFileBackedTaskManagerTest(){
        try {
            File tempFile = File.createTempFile("temp", ".txt");
            FileBackedTaskManager manager = FileBackedTaskManager.loadFromFile(tempFile);
            assertNotNull(manager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}