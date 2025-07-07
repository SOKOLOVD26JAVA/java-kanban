package main.ru.yandex.practicum.http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import main.ru.yandex.practicum.http.adapters.DurationAdapter;
import main.ru.yandex.practicum.http.adapters.LocalDateTimeAdapter;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;


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
            default -> sendMassage(httpExchange, 500, "Ошибка обработки запроса "+method);
        }


    }

    private void get(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length <= 2 && pathParts[1].equals("task")) {
            if (manager.printAllTasks().isEmpty()) {
                sendMassage(httpExchange, 404, "Задач нет");
            } else {
                String allTasks = gson.toJson(manager.printAllTasks());
                sendJsonResponse(httpExchange, allTasks);
            }
        } else if (pathParts.length > 2 && pathParts[1].equals("task")) {
            int id = Integer.parseInt(pathParts[2]);
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
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Task task = gson.fromJson(body, Task.class);
        if (task.getId() == 0) {
            manager.addTask(task);
            sendMassage(httpExchange, 200, "Задача " + task.getName() + " успешно добавлена!");
        } else {
            if (manager.getTaskById(task.getId()) == null) {
                sendMassage(httpExchange, 404, "Задача с ID " + task.getId() + " отсутствует.");
            } else {
                manager.updateTask(task);
                sendMassage(httpExchange, 200, "Задача " + task.getName() + "успешно обновлена!");
            }
        }
    }

    void delete(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length <= 2 && pathParts[1].equals("task")) {
            if (manager.printAllTasks().isEmpty()) {
                sendMassage(httpExchange, 404, "Отсутствуют задачи для удаления.");
            } else {
                manager.removeAllTasks();
                sendMassage(httpExchange, 200, "Задачи успешно удалены.");
            }
        } else if (pathParts.length > 2 && pathParts[1].equals("task")) {
            int id = Integer.parseInt(pathParts[2]);
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
