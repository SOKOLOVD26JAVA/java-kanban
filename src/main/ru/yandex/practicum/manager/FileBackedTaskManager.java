package main.ru.yandex.practicum.manager;

import main.ru.yandex.practicum.model.*;

import java.io.*;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private String filePath;

    public FileBackedTaskManager(String filePath) {
        this.filePath = filePath;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        TaskManager manager = Managers.getDefault();
        try (FileReader reader = new FileReader(file)) {
            BufferedReader bf = new BufferedReader(reader);
            if (bf.ready()) {
                bf.readLine();
            }
            while (bf.ready()) {
                String line = bf.readLine();
                if (line.contains("TASK")) {
                    manager.addTask(fromString(line));
                } else if (line.contains("EPIC")) {
                    manager.addEpic((Epic) fromString(line));
                } else if (line.contains("SUBTASK")) {
                    manager.addSubTask((SubTask) fromString(line));
                }
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка чтения файла");
        }
        return new FileBackedTaskManager("path\\to\\file\\CSV.csv");
    }

    public void save() {
        try (Writer writer = new FileWriter(this.filePath)) {
            writer.write("id,type,name,status,description,epic" + "\n");
            if (!tasks.isEmpty()) {
                for (Task task : tasks.values()) {
                    writer.write(toString(task) + "\n");
                }
            }
            if (!epics.isEmpty()) {
                for (Epic epic : epics.values()) {
                    writer.write(toString(epic) + "\n");
                }
            }
            if (!subTasks.isEmpty()) {
                for (SubTask subTask : subTasks.values()) {
                    writer.write(toString(subTask) + "\n");
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

    private String toString(Task task) {
        String taskInfo = String.format("%s,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(),
                task.getStatus(), task.getDescription());
        return taskInfo;
    }

    private String toString(SubTask task) {
        String taskInfo = String.format("%s,%s,%s,%s,%s,%s", task.getId(), task.getTaskType(), task.getName(),
                task.getStatus(), task.getDescription(), task.getEpicId());
        return taskInfo;
    }

    private static Task fromString(String value) {
        String[] split = value.split(",");
        if (split[1].equals("TASK")) {
            int id = Integer.parseInt(split[0]);
            TaskType type = TaskType.valueOf(split[1]);
            String name = split[2];
            Status status = Status.valueOf(split[3]);
            String description = split[4];
            return new Task(id, type, name, status, description);
        } else if (split[1].equals("EPIC")) {
            int id = Integer.parseInt(split[0]);
            TaskType type = TaskType.valueOf(split[1]);
            String name = split[2];
            Status status = Status.valueOf(split[3]);
            String description = split[4];
            return new Epic(id, type, name, status, description);
        } else {
            int id = Integer.parseInt(split[0]);
            TaskType type = TaskType.valueOf(split[1]);
            String name = split[2];
            Status status = Status.valueOf(split[3]);
            String description = split[4];
            int epicId = Integer.parseInt(split[5]);
            return new SubTask(id, type, name, status, description, epicId);
        }
    }
}
