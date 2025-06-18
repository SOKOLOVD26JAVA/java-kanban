package main.ru.yandex.practicum.manager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InMemoryTaskManager implements TaskManager {

    protected static HashMap<Integer, Task> tasks = new HashMap<>();
    protected static HashMap<Integer, Epic> epics = new HashMap<>();
    protected static HashMap<Integer, SubTask> subTasks = new HashMap<>();

    protected static int id = 1;
    private HistoryManager historyManager = Managers.getDefaultHistory();

    //     Методы для main.ru.yandex.practicum.model.Task
    @Override
    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public ArrayList<Task> printAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        for (Task task: tasks.values()) {
            historyManager.remove(task.getId());
        }
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.addTask(task);
        return task;
    }

    @Override
    public void removeTaskById(int id) {
        Task task = tasks.get(id);
        historyManager.remove(task.getId());
        tasks.remove(task.getId());

    }

    @Override
    public void updateTask(Task newTAsk) {
        Task oldTask = tasks.get(newTAsk.getId());
        if (oldTask != null) {
            tasks.put(newTAsk.getId(), newTAsk);
        } else {
            return;
        }
    }

    //    Методы для main.ru.yandex.practicum.model.Epic
    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Epic> printAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void removeAllEpics() {
        for (Epic epic:epics.values()) {
            historyManager.remove(epic.getId());
        }
        for (SubTask subTask:subTasks.values()) {
            historyManager.remove(subTask.getId());
        }
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        historyManager.addTask(epic);
        return epic;
    }

    @Override
    public void removeEpicById(int id) {
        Epic epic = epics.get(id);
        epics.remove(epic.getId());
        for (Integer subTaskID : epic.getSubTasksID()) {
            subTasks.remove(subTaskID);
            historyManager.remove(subTaskID);
        }
        epic.getSubTasksID().clear();
        historyManager.remove(epic.getId());
    }

    @Override
    public void updateEpic(Epic newEpic) {
        Epic oldEpic = epics.get(newEpic.getId());
        if (oldEpic != null) {
            oldEpic.setName(newEpic.getName());
            oldEpic.setDescription(newEpic.getDescription());

        } else {
            return;
        }

    }

    //    Методы для main.ru.yandex.practicum.model.SubTask
    @Override
    public void addSubTask(SubTask subTask) {
        Epic epic = epics.get(subTask.getEpicId());
        if (epic != null) {
            subTask.setId(generateId());
            subTasks.put(subTask.getId(), subTask);
            epic.addSubTaskID(subTask.getId());
            updateEpicStatus(epic.getId());
        } else {
           return;
        }
    }

    @Override
    public ArrayList<SubTask> printAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void removeAllSubTasks() {
        for (SubTask subTask: subTasks.values()) {
            historyManager.remove(subTask.getId());
        }
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTasksID().clear();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        historyManager.addTask(subTask);
        return subTask;
    }

    @Override
    public void removeSubTaskById(int id) {
        SubTask subTask = subTasks.get(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.getSubTasksID().remove(Integer.valueOf(id));
        subTasks.remove(id);
        updateEpicStatus(epic.getId());
        historyManager.remove(subTask.getId());
    }

    @Override
    public ArrayList<SubTask> getSubTaskByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> subTasksByEpic = new ArrayList<>();
        for (Integer subTasksId : epic.getSubTasksID()) {
            subTasksByEpic.add(subTasks.get(subTasksId));
        }
        return subTasksByEpic;

    }

    @Override
    public void updateSubTask(SubTask newSubTask) {
        SubTask oldSubtask = subTasks.get(newSubTask.getId());
        Epic epic = epics.get(newSubTask.getEpicId());
        if (oldSubtask != null) {
            subTasks.put(newSubTask.getId(), newSubTask);
            epic.getSubTasksID().remove(Integer.valueOf(oldSubtask.getId()));
            updateEpicStatus(epic.getId());
        } else {
            return;
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }

    private void updateEpicStatus(int epicId) {
        int countForNew = 0;
        int countForDone = 0;
        Epic epic = epics.get(epicId);
        if (getSubTaskByEpicId(epic.getId()).isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }
        for (SubTask subTask : getSubTaskByEpicId(epic.getId())) {
            if (subTask.getStatus() == Status.NEW) {
                countForNew++;
            } else if (subTask.getStatus() == Status.DONE) {
                countForDone++;
            } else {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            }
        }
        if (countForNew == getSubTaskByEpicId(epic.getId()).size()) {
            epic.setStatus(Status.NEW);
        } else if (countForDone == getSubTaskByEpicId(epic.getId()).size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }

    private int generateId() {
        return id++;
    }

}






