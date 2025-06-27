package main.ru.yandex.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class SubTask extends main.ru.yandex.practicum.model.Task {
    private int epicId;

    public SubTask(int id, String name, String description, int epicId, Status status) {
        super(id, name, description);
        this.epicId = epicId;
        this.status = status;
    }

    public SubTask(String name, String description, int epicId) {
        super(name, description);
        this.epicId = epicId;
        this.status = Status.NEW;
    }

    public SubTask(int id, String name, Status status, String description, int epicId) {
        super(id, name, status, description);
        this.epicId = epicId;

    }

    public SubTask(int id,
                   String name,
                   Status status,
                   String description,
                   LocalDateTime taskStart,
                   Duration taskDuration, int epicId) {
        super(id, name, status, description, taskStart, taskDuration);
        this.epicId = epicId;

    }

    public SubTask(String name, String description, int epicId, Status status) {
        super(name, description, status);
        this.epicId = epicId;
    }

    public SubTask(String name, String description, LocalDateTime taskStart, Duration taskDuration, int epicId) {
        super(name, description, taskStart, taskDuration);
        this.epicId = epicId;
    }

    public SubTask(String name, Status status, String description, LocalDateTime startTime, Duration taskDuration, int epicId) {
        super(name, status, description, startTime, taskDuration);
        this.epicId = epicId;
    }


    public int getEpicId() {
        return epicId;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.SUBTASK;
    }

    @Override
    public String toString() {
        return "main.ru.yandex.practicum.model.SubTask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
