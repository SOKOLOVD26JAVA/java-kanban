package main.ru.yandex.practicum.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class SubTaskHandler extends BaseHttpHandler {

    public SubTaskHandler(TaskManager manager) {
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
        if (pathParts.length <= 2 && pathParts[1].equals("subtask")) {
            if (manager.printAllSubTasks().isEmpty()) {
                sendMassage(httpExchange, 404, "Подзадач нет");
            } else {
                String allTasks = gson.toJson(manager.printAllSubTasks());
                sendJsonResponse(httpExchange, allTasks);
            }
        } else if (pathParts.length > 2 && pathParts[1].equals("subtasks")) {
            int id = Integer.parseInt(pathParts[2]);
            SubTask task = manager.getSubTaskById(id);
            if (task == null) {
                sendMassage(httpExchange, 404, "Подзадача c ID " + id + " отсутствует");
            } else {
                String sendTask = gson.toJson(task, SubTask.class);
                sendJsonResponse(httpExchange, sendTask);
            }
        } else {
            sendMassage(httpExchange, 500, "Указанный путь " + pathParts[1] + " не корректен.");
        }
    }


    private void post(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        SubTask task = gson.fromJson(body, SubTask.class);

        if (task.getId() == 0) {
            if(task.getEpicId() == 0 || manager.getEpicById(task.getEpicId()) == null){
                sendMassage(httpExchange,404,"Эпик для подзадачи не найден");
            }else {
                manager.addSubTask(task);
                sendMassage(httpExchange, 200, "Подзадача " + task.getName() + "успешно добавлена!");
            }
        } else {
            if (manager.getSubTaskById(task.getId()) == null) {
                sendMassage(httpExchange, 404, "Подзадача с ID " + task.getId() + " отсутствует.");
            } else {
                manager.updateSubTask(task);
                sendMassage(httpExchange, 200, "Подзадача " + task.getName() + "успешно обновлена!");
            }
        }
    }

    void delete(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length <= 2 && pathParts[1].equals("subtask")) {
            if (manager.printAllSubTasks().isEmpty()) {
                sendMassage(httpExchange, 404, "Отсутствуют подзадачи для удаления.");
            } else {
                manager.removeAllSubTasks();
                sendMassage(httpExchange, 200, "подзадачи успешно удалены.");
            }
        } else if (pathParts.length > 2 && pathParts[1].equals("subtask")) {
            int id = Integer.parseInt(pathParts[2]);
            Task task = manager.getSubTaskById(id);
            if (task == null) {
                sendMassage(httpExchange, 404, "Подзадача c ID " + id + " отсутствует.");
            } else {
                manager.removeSubTaskById(task.getId());
                sendMassage(httpExchange, 200, "Подзадача успешно удалена.");
            }
        } else {
            sendMassage(httpExchange, 500, "Указанный путь " + pathParts[1] + " не корректен.");
        }

    }
}
