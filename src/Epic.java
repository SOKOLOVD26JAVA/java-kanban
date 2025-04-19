import java.util.ArrayList;

public class Epic extends Task {
    ArrayList<SubTask> subTasks;

    public Epic(String name, String description, int id) {
        super(id, name, description);
        this.status = Status.NEW;
        this.subTasks = new ArrayList<>();
    }


    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTasks=" + subTasks +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
