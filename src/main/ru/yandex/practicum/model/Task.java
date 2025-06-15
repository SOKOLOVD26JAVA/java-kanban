package main.ru.yandex.practicum.model;

import java.util.Objects;

public class Task {
    protected int id;
    protected  String name;
    protected  String description;
    protected Status status;
    protected TaskType taskType;


    public Task(int id,String name,String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.taskType = TaskType.TASK;
    }

    public Task(int id,String name,String description,Status status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.taskType = TaskType.TASK;
    }

    public Task(String name,String description) {
        this.name = name;
        this.description = description;
        this.status = Status.NEW;
        this.taskType = TaskType.TASK;
    }
    public Task(int id, TaskType type,String name,Status status,String description) {
        this.id = id;
        this.taskType = type;
        this.name = name;
        this.status = status;
        this.description = description;
    }

    public TaskType getTaskType() {
        return taskType;
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
}
