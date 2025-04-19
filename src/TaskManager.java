
import java.util.HashMap;


public class TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HashMap<Integer, SubTask> subTaskss = new HashMap<>();
    private int id = 1;

    public int setId() {
        id++;
        return id;
    }
    //     Методы для Task
    public void addTAsk(Task task) {
        tasks.put(task.getId(), task);
    }

    public void printAllTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void getTaskById(int id) {
        System.out.println(tasks.get(id));

    }

    public void removeTaskById(int id){
        tasks.remove(id);

    }
    public void updateTaskStatus(Task oldTask,Task newTAsk){
        newTAsk.setId(oldTask.getId());
        tasks.put(newTAsk.getId(), newTAsk);
    }
    public void updateStatusForTaskToIN_PROGRESS(Task task){
        task.setStatus(Status.IN_PROGRESS);
    }
    public void updateStatusForTaskToDONE(Task task){
        task.setStatus(Status.DONE);
    }
    //    Методы для Epic
    public void addEpic(Epic epic){
        epics.put(epic.getId(),epic);
    }

    public void printAllEpics(){
        for (Epic epic: epics.values()){
            System.out.println(epic);
        }
    }

    public void removeAllEpics(){
        epics.clear();
    }

    public void getEpicById(Epic epic){
        System.out.println(epics.get(epic.getId()));
    }

    public void removeEpicById(int id){
        epics.remove(id);
    }
    public void updateEpicStatus(Epic epic){
        for(SubTask subTask :epic.subTasks){
            if(subTask.getStatus() == Status.DONE){
                epic.setStatus(Status.DONE);
            }else{
                epic.setStatus(Status.IN_PROGRESS);
            }
        }

    }

    //    Методы для SubTask
    public void addSubTask(SubTask subTask){
        Epic epic = epics.get(subTask.getEpicId());
        subTaskss.put(subTask.getId(),subTask);
        epic.addSubTask(subTask);
    }

    public void printAllSubTasks(){
        for (SubTask subTask: subTaskss.values()){
            System.out.println(subTask);
        }
    }

    public void removeAllSubTasks(){
        subTaskss.clear();
    }

    public void getSubTaskById(int id){
        System.out.println(subTaskss.get(id));
    }

    public void removeSubTaskById(int id){
        subTaskss.remove(id);
    }

    public void printSubTaskByEpicId(Epic epic){
        System.out.println(epic.subTasks);
    }
    public void updateSubTaskStatus(SubTask oldSubtask,SubTask newSubTask,Epic epic){
        newSubTask.setId(oldSubtask.getId());
        subTaskss.put(newSubTask.getId(), newSubTask);
        epic.subTasks.remove(oldSubtask);
        epic.subTasks.add(newSubTask);


    }
    public void updateStatusForSubTaskToIN_PROGRESS(SubTask subTask){
        subTask.setStatus(Status.IN_PROGRESS);
    }
    public void updateStatusForSubTaskToDONE(SubTask subTask){
        subTask.setStatus(Status.DONE);
    }

}






