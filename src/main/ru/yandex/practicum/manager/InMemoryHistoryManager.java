package main.ru.yandex.practicum.manager;


import main.ru.yandex.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history = new ArrayList<>();
    private static final int MAX_HISTORY_SIZE = 10;

    @Override
    public void addTask(Task task) {
        if (task == null) {
            return;
        } else {
            history.add(task);
            checkHistorySize();
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
    }

    private void checkHistorySize() {
        if (history.size() > MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
    }

}
