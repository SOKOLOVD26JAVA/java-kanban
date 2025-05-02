package ru.yandex.practicum.manager;

import ru.yandex.practicum.model.Epic;
import ru.yandex.practicum.model.SubTask;
import ru.yandex.practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    //     Методы для ru.yandex.practicum.model.Task
   void addTask(Task task);

    //
    ArrayList<Task> printAllTasks();

    //
    void removeAllTasks();

    Task getTaskById(int id);

    void removeTaskById(int id);

    void updateTask(Task newTAsk);

    //    Методы для ru.yandex.practicum.model.Epic
    void addEpic(Epic epic);

    ArrayList<Epic> printAllEpics();

    void removeAllEpics();

    Epic getEpicById(int id);

    void removeEpicById(int id);

    void updateEpic(Epic newEpic);

    //    Методы для ru.yandex.practicum.model.SubTask
    void addSubTask(SubTask subTask);

    ArrayList<SubTask> printAllSubTasks();

    void removeAllSubTasks();

    SubTask getSubTaskById(int id);

    void removeSubTaskById(int id);

    ArrayList<SubTask> getSubTaskByEpicId(int epicId);

    void updateSubTask(SubTask newSubTask);
   List<Task> getHistory();
}
