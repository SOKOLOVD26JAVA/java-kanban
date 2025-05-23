package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;

public class Main {
    public static void main(String[] args) {
        HistoryManager history = Managers.getDefaultHistory();
        TaskManager manager = Managers.getDefault();
        Epic epic1 = new Epic("1","2");
        manager.addEpic(epic1);
        SubTask subTask1 = new SubTask("1.1","1", epic1.getId());
        manager.addSubTask(subTask1);
        SubTask subTask2 = new SubTask("1.2","2", epic1.getId());
        manager.addSubTask(subTask2);
        SubTask subTask3 = new SubTask("1.3","3", epic1.getId());
        manager.addSubTask(subTask3);
        System.out.println(epic1.getSubTasksID());
        manager.removeSubTaskById(subTask1.getId());
        System.out.println(epic1.getSubTasksID());
    }
}
