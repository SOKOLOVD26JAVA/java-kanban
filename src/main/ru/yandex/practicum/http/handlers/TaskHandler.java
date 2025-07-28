package main.ru.yandex.practicum.http.handlers;


import com.sun.net.httpserver.HttpExchange;

import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Task;

import java.io.IOException;


public class TaskHandler extends BaseHttpHandler {

    public TaskHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        switch (method) {
            case "GET" -> get(httpExchange);
            case "POST" -> post(httpExchange);
            case "DELETE" -> delete(httpExchange);
            default -> sendMassage(httpExchange, 500, "Ошибка обработки запроса " + method);
        }


    }

    private void get(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length <= 2) {
            if (manager.printAllTasks().isEmpty()) {
                sendMassage(httpExchange, 404, "Задач нет");
            } else {
                String allTasks = gson.toJson(manager.printAllTasks());
                sendJsonResponse(httpExchange, allTasks);
            }
        } else if (pathParts.length > 2) {
            int id = getIdFromPathParts(pathParts);
            Task task = manager.getTaskById(id);
            if (task == null) {
                sendMassage(httpExchange, 404, "Задача c ID " + id + " отсутствует");
            } else {
                String sendTask = gson.toJson(task, Task.class);
                sendJsonResponse(httpExchange, sendTask);
            }
        } else {
            sendMassage(httpExchange, 500, "Указанный путь " + pathParts[1] + " не корректен.");
        }
    }


    private void post(HttpExchange httpExchange) throws IOException {
        String body = getBody(httpExchange);
        Task task = gson.fromJson(body, Task.class);
        if (!(task.getTaskStart() == null) && !(task.getTaskDuration() == null)) {
            if (task.getId() == 0) {
                if (manager.getPrioritizedTasks().contains(task)) {
                    sendMassage(httpExchange, 406, "Задача " +
                            task.getName() + " пересекается с существующими");
                    return;
                }
                manager.addTask(task);
                if (manager.getPrioritizedTasks().contains(task)) {
                    sendMassage(httpExchange, 200, "Задача " +
                            task.getName() + " успешно добавлена!");
                } else {
                    sendMassage(httpExchange, 406, "Задача " +
                            task.getName() + " пересекается с существующими");
                }
            } else {
                if (manager.getTaskById(task.getId()) == null) {
                    sendMassage(httpExchange, 404, "Задача с ID " +
                            task.getId() + " отсутствует.");
                    return;
                }
                if (manager.getPrioritizedTasks().contains(task)) {
                    sendMassage(httpExchange, 406, "Задача " +
                            task.getName() + " пересекается с существующими");
                    return;
                }
                manager.updateTask(task);
                if (manager.getPrioritizedTasks().contains(task)) {
                    sendMassage(httpExchange, 200, "Задача " +
                            task.getName() + " успешно обновлена!");
                } else {
                    sendMassage(httpExchange, 406, "Задача " +
                            task.getName() + " пересекается с существующими");
                }
            }
        } else {
            if (task.getId() == 0) {
                manager.addTask(task);
                sendMassage(httpExchange, 200, "Задача " + task.getName() + " успешно добавлена!");
            } else {
                if (manager.getTaskById(task.getId()) == null) {
                    sendMassage(httpExchange, 404, "Задача с ID " + task.getId() + " отсутствует.");
                } else {
                    manager.updateTask(task);
                    sendMassage(httpExchange, 200, "Задача " + task.getName() + " успешно обновлена!");
                }
            }
        }
    }

    void delete(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length <= 2) {
            if (manager.printAllTasks().isEmpty()) {
                sendMassage(httpExchange, 404, "Отсутствуют задачи для удаления.");
            } else {
                manager.removeAllTasks();
                sendMassage(httpExchange, 200, "Задачи успешно удалены.");
            }
        } else if (pathParts.length > 2) {
            int id = getIdFromPathParts(pathParts);
            Task task = manager.getTaskById(id);
            if (task == null) {
                sendMassage(httpExchange, 404, "Задача c ID " + id + " отсутствует.");
            } else {
                manager.removeTaskById(task.getId());
                sendMassage(httpExchange, 200, "Задача успешно удалена.");
            }
        } else {
            sendMassage(httpExchange, 500, "Указанный путь " + pathParts[1] + " не корректен.");
        }

    }
}
