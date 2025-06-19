package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.*;

public class CSVTaskConverter {

    public static String taskToString(Task task) {
        if (task.getTaskType() == TaskType.SUBTASK) {
            SubTask subTask = (SubTask) task;
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
        TaskType taskType = TaskType.valueOf(split[1]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];
        switch (taskType) {
            case TASK -> {
                return new Task(id, name, status, description);
            }
            case EPIC -> {
                return new Epic(id, name, status, description);
            }
            case SUBTASK -> {
                int epicId = Integer.parseInt(split[5]);
            return new SubTask(id, name, status, description, epicId);
            }
            default -> throw new ManagerSaveException("Ошибка");
        }
    }
}
