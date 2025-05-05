package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.Task;

import java.util.List;

public interface HistoryManager {

    void addTask(Task task);

    List<Task> getHistory();
}
