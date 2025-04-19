import java.util.Objects;

public class Task {
    public final String name;
    public final String description;
    public int id;
    Status status;


    public Task(int id ,String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
    public Task(int id ,String name,String description,Status status){
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
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
        return id == task.id && Objects.equals(name, task.name) && Objects.equals(description, task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }

}
