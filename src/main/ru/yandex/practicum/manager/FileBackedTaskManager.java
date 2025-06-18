package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.*;

import java.io.*;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private String filePath;

    public FileBackedTaskManager() {
     String defaultFile = this.filePath;
    }

    public FileBackedTaskManager(String filePath) {
        this.filePath = filePath;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        String pathToFile = file.getPath();
        FileBackedTaskManager manager = new FileBackedTaskManager(pathToFile);
        try (FileReader reader = new FileReader(file)) {
            BufferedReader bf = new BufferedReader(reader);
            if (bf.ready()) {
                bf.readLine();
            }
            while (bf.ready()) {
                String line = bf.readLine();
                Task task = CSVTaskConverter.fromString(line);
                if (task.getTaskType().equals(TaskType.TASK)) {
                    tasks.put(task.getId(), task);
                    updateMaxId(task);
                }
                if (task.getTaskType().equals(TaskType.EPIC)) {
                    Epic epic = (Epic) task;
                    epics.put(epic.getId(), epic);
                    updateMaxId(epic);
                }
                if (task.getTaskType().equals(TaskType.SUBTASK)) {
                    SubTask subTask = (SubTask) task;
                    subTasks.put(subTask.getId(), subTask);
                    updateMaxId(subTask);
                }
                if (!subTasks.isEmpty()) {
                    for (SubTask subTask : subTasks.values()) {
                        Epic epic = epics.get(subTask.getEpicId());
                        epic.addSubTaskID(subTask.getId());
                    }
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла");
        }
        return manager;
    }

    public void save() {
        try (Writer writer = new FileWriter(this.filePath)) {
            writer.write("id,type,name,status,description,epic" + "\n");
            if (!tasks.isEmpty()) {
                for (Task task : tasks.values()) {
                    writer.write(CSVTaskConverter.taskToString(task) + "\n");
                }
            }
            if (!epics.isEmpty()) {
                for (Epic epic : epics.values()) {
                    writer.write(CSVTaskConverter.taskToString(epic) + "\n");
                }
            }
            if (!subTasks.isEmpty()) {
                for (SubTask subTask : subTasks.values()) {
                    writer.write(CSVTaskConverter.taskToString(subTask) + "\n");
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка записи в файл.");
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void updateTask(Task newTAsk) {
        super.updateTask(newTAsk);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void updateEpic(Epic newEpic) {
        super.updateEpic(newEpic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
        save();
    }

    @Override
    public void updateSubTask(SubTask newSubTask) {
        super.updateSubTask(newSubTask);
        save();
    }

    private static void updateMaxId(Task task) {
        if (task.getId() > id) {
            id = task.getId();
        }
    }

}
