package ru.yandex.practicum.manager;


import ru.yandex.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List <Task> history = new ArrayList<>();

    @Override
    public  void addTask(Task task){
        checkHistorySize();
        history.add(task);
    }
    @Override
    public List <Task> getHistory(){
        return new ArrayList<>(history);
    }
    private void checkHistorySize(){
        if(history.size()>9){
            history.removeFirst();
        }
    }

}
