package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CSVTaskConverter {

    public static String taskToString(Task task) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        if (task.getTaskType() == TaskType.SUBTASK) {
            SubTask subTask = (SubTask) task;
            if (subTask.getTaskStart() == null || subTask.getTaskDuration() == null || subTask.getEndTime() == null) {
                return String.format("%s,%s,%s,%s,%s,%s", subTask.getId(), subTask.getTaskType(), subTask.getName(),
                        subTask.getStatus(), subTask.getDescription(), subTask.getEpicId());
            } else {
                return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s", subTask.getId(),
                        subTask.getTaskType(), subTask.getName(),
                        subTask.getStatus(), subTask.getDescription(), subTask.getTaskStart().format(format),
                        subTask.getTaskDuration().toMinutes(), subTask.getEndTime().format(format),
                        subTask.getEpicId());
            }
        } else if (task.getTaskType() == TaskType.EPIC) {
            Epic epic = (Epic) task;
            if (epic.getTaskDuration() == null || epic.getTaskStart() == null || epic.getEndTime() == null) {
                return String.format("%s,%s,%s,%s,%s", epic.getId(), epic.getTaskType(), epic.getName(),
                        epic.getStatus(), epic.getDescription());

            } else {
                return String.format("%s,%s,%s,%s,%s,%s,%s,%s", epic.getId(), epic.getTaskType(), epic.getName(),
                        epic.getStatus(), epic.getDescription(), epic.getTaskStart().format(format), epic.getTaskDuration().toMinutes(),
                        epic.getEndTime().format(format));
            }
        } else {
            if (task.getTaskStart() == null || task.getTaskDuration() == null || task.getEndTime() == null) {
                return String.format("%s,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(),
                        task.getStatus(), task.getDescription());
            } else {
                return String.format("%s,%s,%s,%s,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(),
                        task.getStatus(), task.getDescription(), task.getTaskStart().format(format), task.getTaskDuration().toMinutes(),
                        task.getEndTime().format(format));
            }
        }
    }

    public static Task fromString(String value) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String[] split = value.split(",");
        int id = Integer.parseInt(split[0]);
        TaskType taskType = TaskType.valueOf(split[1]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];
        if (split.length >= 5) {
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
        } else {
            LocalDateTime startTime = LocalDateTime.parse(split[5], format);
            Duration duration = Duration.ofMinutes(Long.parseLong(split[6]));
            switch (taskType) {
                case TASK -> {
                    return new Task(id, name, status, description, startTime, duration);
                }
                case EPIC -> {
                    return new Epic(id, name, status, description, startTime, duration);
                }
                case SUBTASK -> {
                    int epicId = Integer.parseInt(split[8]);
                    return new SubTask(id, name, status, description, startTime, duration, epicId);
                }
                default -> throw new ManagerSaveException("Ошибка");
            }
        }
    }
}
