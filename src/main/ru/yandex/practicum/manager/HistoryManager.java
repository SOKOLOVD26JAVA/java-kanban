package main.ru.yandex.practicum.manager;
import main.ru.yandex.practicum.model.Task;
import java.util.ArrayList;


public interface HistoryManager {

    void addTask(Task task);

    void remove(int id);

    ArrayList<Task> getHistory();
}
