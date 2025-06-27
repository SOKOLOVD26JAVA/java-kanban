package main.ru.yandex.practicum.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Epic extends main.ru.yandex.practicum.model.Task {

    private ArrayList<Integer> subTasksID = new ArrayList<>();
    LocalDateTime endTime;

    public Epic(String name, String description) {
        super(name, description);
        this.status = Status.NEW;
    }

    public Epic(int id, String name, String description) {
        super(id, name, description);
        this.status = Status.NEW;
    }

    public Epic(int id, String name, Status status, String description) {
        super(id, name, status, description);
    }

    public Epic(int id,
                String name,
                Status status,
                String description,
                LocalDateTime startTime,
                Duration taskDuration) {
        super(id, name, status, description, startTime, taskDuration);
    }

    public void addSubTaskID(int subTaskId) {
        subTasksID.add(subTaskId);
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.EPIC;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "main.ru.yandex.practicum.model.Epic{" +
                "subTasksID=" + subTasksID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

    public ArrayList<Integer> getSubTasksID() {
        return subTasksID;
    }

    public void setSubTasksID(ArrayList<Integer> subTasksID) {
        this.subTasksID = subTasksID;
    }

}
