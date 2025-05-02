package ru.yandex.practicum.manager;

import ru.yandex.practicum.model.Task;

import java.util.List;

public interface HistoryManager {

    void addTask(Task task);

    List<Task> getHistory();
}
