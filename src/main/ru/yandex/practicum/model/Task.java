package main.ru.yandex.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class Task {
    protected int id;
    protected String name;
    protected String description;
    protected Status status;
    protected LocalDateTime taskStart;
    protected Duration taskDuration;


    public Task(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Task(int id, String name, String description, Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
    }

    public Task(int id, String name, Status status, String description) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public Task(int id, String name, Status status, String description, LocalDateTime taskStart, Duration taskDuration) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.description = description;
        this.taskStart = taskStart;
        this.taskDuration = taskDuration;
    }

    public Task(String name, String description, Status status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public Task(String name, Status status, String description, LocalDateTime taskStart, Duration taskDuration) {
        this.status = status;
        this.name = name;
        this.description = description;
        this.taskDuration = taskDuration;
        this.taskStart = taskStart;
    }

    public Task(String name, String description, LocalDateTime taskStart, Duration taskDuration) {
        this.name = name;
        this.description = description;
        this.taskStart = taskStart;
        this.taskDuration = taskDuration;
    }


    public TaskType getTaskType() {
        return TaskType.TASK;
    }

    public int getId() {
        return id;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getEndTime() {
        return this.taskStart.plus(this.taskDuration);
    }


    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Task task = (Task) object;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "main.ru.yandex.practicum.model.Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setTaskDuration(Duration taskDuration) {
        this.taskDuration = taskDuration;
    }

    public void setTaskStart(LocalDateTime taskStart) {
        this.taskStart = taskStart;
    }

    public LocalDateTime getTaskStart() {
        return taskStart;
    }

    public Duration getTaskDuration() {
        return taskDuration;
    }
}
