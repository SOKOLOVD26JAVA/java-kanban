package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;

public class CSVTaskConverter {

    public static String taskToString(Task task) {
        if(task.getClass() == SubTask.class) {
            SubTask subTask = (SubTask)task;
            String taskInfo = String.format("%s,%s,%s,%s,%s,%s", subTask.getId(), subTask.getTaskType(), subTask.getName(),
                    subTask.getStatus(), subTask.getDescription(), subTask.getEpicId());
            return taskInfo;
        } else {
            String taskInfo = String.format("%s,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(),
                    task.getStatus(), task.getDescription());
            return taskInfo;
        }
    }

    public static Task fromString(String value) {
        String[] split = value.split(",");
        int id = Integer.parseInt(split[0]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];
        if (split[1].equals("TASK")) {
            return new Task(id,name, status, description);
        } else if (split[1].equals("EPIC")) {
            return new Epic(id, name, status, description);
        } else {
            int epicId = Integer.parseInt(split[5]);
            return new SubTask(id, name, status, description, epicId);
        }
    }
}
