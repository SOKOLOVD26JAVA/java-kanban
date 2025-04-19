public class SubTask extends Task{
    private int epicId;
    public SubTask(int id,String name,String description,int epicId,Status status){
        super(id,name,description);
        this.epicId = epicId;
        this.status = status;
    }
    public SubTask(int id,String name,String description,int epicId){
        super(id,name,description);
        this.epicId = epicId;
        this.status = Status.NEW;
    }

    public int getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
