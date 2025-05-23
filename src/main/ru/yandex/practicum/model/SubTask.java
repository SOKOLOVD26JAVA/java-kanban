package main.ru.yandex.practicum.model;

public class SubTask extends main.ru.yandex.practicum.model.Task {
    private int epicId;
    public SubTask(int id,String name,String description,int epicId,Status status){
        super(id,name,description);
        this.epicId = epicId;
        this.status = status;
    }
    public SubTask(String name,String description,int epicId){
        super(name,description);
        this.epicId = epicId;
        this.status = Status.NEW;
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
