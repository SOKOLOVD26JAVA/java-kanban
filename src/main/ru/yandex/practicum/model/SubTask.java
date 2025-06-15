package main.ru.yandex.practicum.model;

public class SubTask extends main.ru.yandex.practicum.model.Task {
    private int epicId;

    public SubTask(int id,String name,String description,int epicId,Status status) {
        super(id,name,description);
        this.epicId = epicId;
        this.status = status;
        this.taskType = TaskType.SUBTASK;
    }

    public SubTask(String name,String description,int epicId) {
        super(name,description);
        this.epicId = epicId;
        this.status = Status.NEW;
        this.taskType = TaskType.SUBTASK;
    }
    public SubTask(int id,TaskType type,String name,Status status,String description,int epicId){
        super(id,type, name, status, description);
        this.epicId = epicId;

    }

    public int getEpicId() {
        return epicId;
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
