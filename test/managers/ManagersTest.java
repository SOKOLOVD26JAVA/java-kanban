package managers;

import main.ru.yandex.practicum.manager.FileBackedTaskManager;
import main.ru.yandex.practicum.manager.HistoryManager;
import main.ru.yandex.practicum.manager.Managers;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Task;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

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