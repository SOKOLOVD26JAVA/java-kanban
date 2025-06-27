package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.Status;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


public class InMemoryTaskManager implements TaskManager {

    protected HashMap<Integer, Task> tasks = new HashMap<>();
    protected HashMap<Integer, Epic> epics = new HashMap<>();
    protected HashMap<Integer, SubTask> subTasks = new HashMap<>();
    protected TreeSet<Task> allTasks = new TreeSet<>(Comparator.comparing(Task::getTaskStart));
    protected int id = 1;
    private HistoryManager historyManager = Managers.getDefaultHistory();

    //     Методы для main.ru.yandex.practicum.model.Task
    @Override
    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
        checkAndAdd(task);
    }

    @Override
    public ArrayList<Task> printAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void removeAllTasks() {
        for (Task task : tasks.values()) {
            historyManager.remove(task.getId());
            allTasks.remove(task);
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
        allTasks.remove(task);

    }

    @Override
    public void updateTask(Task newTask) {
        Task oldTask = tasks.get(newTask.getId());
        if (oldTask != null) {
            tasks.put(newTask.getId(), newTask);
            allTasks.remove(oldTask);
            checkAndAdd(newTask);
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
        for (Epic epic : epics.values()) {
            historyManager.remove(epic.getId());
        }
        for (SubTask subTask : subTasks.values()) {
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
        for (Integer subTaskID : epic.getSubTasksID()) {
            subTasks.remove(subTaskID);
            historyManager.remove(subTaskID);
        }
        epics.remove(epic.getId());
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
            if (!(subTask.getTaskStart() == null) || !(subTask.getTaskDuration() == null)) {
                updateEpicTimes(epic.getId());
            }
            checkAndAdd(subTask);
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
        for (SubTask subTask : subTasks.values()) {
            historyManager.remove(subTask.getId());
            allTasks.remove(subTask);
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
        if (!(subTask.getTaskStart() == null) || !(subTask.getTaskDuration() == null)) {
            updateEpicTimes(epic.getId());
        }
        historyManager.remove(subTask.getId());
        allTasks.remove(subTask);

    }

    @Override
    public List<SubTask> getSubTaskByEpicId(int epicId) {
        Epic epic = epics.get(epicId);
        return epic.getSubTasksID().stream().map(subTasks::get).
                collect(Collectors.toList());
    }

    @Override
    public void updateSubTask(SubTask newSubTask) {
        SubTask oldSubtask = subTasks.get(newSubTask.getId());
        allTasks.remove(oldSubtask);
        Epic epic = epics.get(newSubTask.getEpicId());
        if (oldSubtask != null) {
            subTasks.put(newSubTask.getId(), newSubTask);
            checkAndAdd(newSubTask);
            epic.getSubTasksID().remove(Integer.valueOf(oldSubtask.getId()));
            epic.getSubTasksID().add(newSubTask.getId());
            updateEpicStatus(epic.getId());
            if (!(newSubTask.getTaskStart() == null) || !(newSubTask.getTaskDuration() == null)) {
                updateEpicTimes(epic.getId());
            }
        } else {
            return;
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(historyManager.getHistory());
    }

    private boolean crossForTasks(Task task) {
        return allTasks.stream().
                filter(task1 -> !task1.equals(task)).
                anyMatch(task1 -> crossForTwoTasks(task1, task));
    }


    private boolean crossForTwoTasks(Task task1, Task task2) {
        return task1.getEndTime().isAfter(task2.getTaskStart()) && task2.getEndTime().isAfter(task1.getTaskStart());
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return new TreeSet<>(allTasks);
    }


    private void checkAndAdd(Task task) {
        if (task.getTaskStart() == null || task.getTaskDuration() == null) {
            return;
        }
        if (allTasks.isEmpty()) {
            allTasks.add(task);
        } else if (!crossForTasks(task)) {
            allTasks.add(task);
        }
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

    private void updateEpicTimes(int epicId) {
        Epic epic = epics.get(epicId);
        if (getSubTaskByEpicId(epic.getId()).isEmpty()) {
            return;
        }
        epic.setTaskDuration(getSubTaskByEpicId(epic.getId()).stream()
                .map(SubTask::getTaskDuration).
                reduce(Duration.ZERO, Duration::plus));
        epic.setTaskStart(getSubTaskByEpicId(epic.getId()).stream().
                map(SubTask::getTaskStart).min(LocalDateTime::compareTo).get());
        epic.setEndTime(getSubTaskByEpicId(epic.getId()).stream().map(SubTask::getEndTime)
                .max(LocalDateTime::compareTo).get());
    }

    private int generateId() {
        return id++;
    }

}






