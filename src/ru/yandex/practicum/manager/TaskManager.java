package ru.yandex.practicum.manager;

import ru.yandex.practicum.model.Epic;
import ru.yandex.practicum.model.Status;
import ru.yandex.practicum.model.SubTask;
import ru.yandex.practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected int id = 1;

    //     Методы для ru.yandex.practicum.model.Task
    public void addTAsk(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }
    //
    public ArrayList<Task> printAllTasks() {
        return new ArrayList<>(tasks.values());
    }
    //
    public void removeAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void removeTaskById(int id){
        tasks.remove(id);

    }
    public void updateTask(Task newTAsk){
        Task oldTAsk = tasks.get(newTAsk.getId());
        if(oldTAsk != null) {
            tasks.put(newTAsk.getId(), newTAsk);
        }else {
            return;
        }
    }

    //    Методы для ru.yandex.practicum.model.Epic
    public void addEpic(Epic epic){
        epic.setId(generateId());
        epics.put(epic.getId(),epic);
    }

    public ArrayList<Epic> printAllEpics(){
        return new ArrayList<>(epics.values());
    }

    public void removeAllEpics(){
        epics.clear();
        subTasks.clear();
    }

    public Epic getEpicById(int id){
        return epics.get(id);
    }

    public void removeEpicById(int id){
        Epic epic = epics.get(id);
        epics.remove(epic.getId());
        for(Integer subTaskID:epic.getSubTasksID()){
            subTasks.remove(subTaskID);
        }
        epic.getSubTasksID().clear();
    }

    public void updateEpic(Epic newEpic){
        Epic oldEpic = epics.get(newEpic.getId());
        if (oldEpic != null){
            oldEpic.setName(newEpic.getName());
            oldEpic.setDescription(newEpic.getDescription());

        }else {
            return;
        }

    }

    //    Методы для ru.yandex.practicum.model.SubTask
    public void addSubTask(SubTask subTask){
        Epic epic = epics.get(subTask.getEpicId());
        if(epic != null) {
            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTaskID(subTask.getId());
            updateEpicStatus(epic);
        }else {
            return;
        }
    }

    public ArrayList<SubTask> printAllSubTasks(){
        return new ArrayList<>(subTasks.values());
    }

    public void removeAllSubTasks(){
        subTasks.clear();
        for(Epic epic:epics.values()){
            epic.getSubTasksID().clear();
        }
    }

    public SubTask getSubTaskById(int id){
        return subTasks.get(id);
    }

    public void removeSubTaskById(int id){
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getSubTasksID().remove(Integer.valueOf(id));
        subTasks.remove(id);

    }

    public ArrayList<SubTask> printSubTaskByEpicId(Epic epic){
        ArrayList<SubTask> subTasksByEpic = new ArrayList<>();
        for(Integer subTasksId:epic.getSubTasksID()){
            subTasksByEpic.add(subTasks.get(subTasksId));
        }
        return subTasksByEpic;

    }
    public void updateSubTaskStatus(SubTask newSubTask) {
        SubTask oldSubtask = subTasks.get(newSubTask.getId());
        Epic epic = epics.get(newSubTask.getEpicId());
        if (oldSubtask != null) {
            newSubTask.setId(oldSubtask.getId());
            subTasks.put(newSubTask.getId(), newSubTask);
            epic.getSubTasksID().remove(Integer.valueOf(oldSubtask.getId()));
            epic.getSubTasksID().add(newSubTask.getId());
            updateEpicStatus(epic);
        }else {
            return;
        }
    }
    private void updateEpicStatus(Epic epic){
        for(Integer subTaskID :epic.getSubTasksID()){
            if(subTasks.get(subTaskID).getStatus() == Status.NEW){
                epic.setStatus(Status.NEW);
            }else if(subTasks.get(subTaskID).getStatus() == Status.DONE){
                epic.setStatus(Status.DONE);
            }else{
                epic.setStatus(Status.IN_PROGRESS);
            }
        }

    }
    private int generateId() {
        return id++;
    }

}






