package main.ru.yandex.practicum.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Epic;
import main.ru.yandex.practicum.model.SubTask;
import main.ru.yandex.practicum.model.Task;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EpicHandler extends BaseHttpHandler {

    public EpicHandler(TaskManager manager) {
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
        if (pathParts.length <= 2 && pathParts[1].equals("epic")) {
            if (manager.printAllEpics().isEmpty()) {
                sendMassage(httpExchange, 404, "Эпиков нет");
            } else {
                String allTasks = gson.toJson(manager.printAllEpics());
                sendJsonResponse(httpExchange, allTasks);
            }
        } else if (pathParts.length == 3 && pathParts[1].equals("epic")) {
            int id = Integer.parseInt(pathParts[2]);
            Epic epic = manager.getEpicById(id);
            if (epic == null) {
                sendMassage(httpExchange, 404, "Эпик c ID " + id + " отсутствует");
            } else {
                String sendEpic = gson.toJson(epic, Epic.class);
                sendJsonResponse(httpExchange, sendEpic);
            }
        } else if (pathParts.length == 4 && pathParts[1].equals("epic") && pathParts[3].equals("subtasks")) {
            int id = Integer.parseInt(pathParts[2]);
            Epic epic = manager.getEpicById(id);
            if (epic == null) {
                sendMassage(httpExchange, 404, "Эпик c ID " + id + " отсутствует");
            } else {
                if (epic.getSubTasksID() == null || epic.getSubTasksID().isEmpty()) {
                    sendMassage(httpExchange, 404, "У " + epic.getName() + " отсутствуют подзадачи");
                } else {
                    ArrayList<SubTask> subTasks = new ArrayList<>();
                    for (int subTaskId : epic.getSubTasksID()) {
                        SubTask subTask = manager.getSubTaskById(subTaskId);
                        subTasks.add(subTask);
                    }
                    String epicSubtasks = gson.toJson(subTasks);
                    sendJsonResponse(httpExchange, epicSubtasks);
                }
            }

        } else {
            sendMassage(httpExchange, 500, "Указанный путь не корректен.");
        }
    }


    private void post(HttpExchange httpExchange) throws IOException {
        InputStream inputStream = httpExchange.getRequestBody();
        String body = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        Epic epic = gson.fromJson(body, Epic.class);
        Epic epic1 = new Epic(epic.getId(), epic.getName(), epic.getDescription());
        if (epic.getId() == 0) {
            manager.addEpic(epic1);
            sendMassage(httpExchange, 200, "Задача " + epic.getName() + " успешно добавлена!");
            System.out.println("fas");
        } else {
            if (manager.getEpicById(epic.getId()) == null) {
                sendMassage(httpExchange, 404, "Задача с ID " + epic.getId() + " отсутствует.");
            } else {
                manager.updateEpic(epic);
                sendMassage(httpExchange, 200, "Задача " + epic.getName() + "успешно обновлена!");
            }
        }
    }

    void delete(HttpExchange httpExchange) throws IOException {
        String path = httpExchange.getRequestURI().getPath();
        String[] pathParts = path.split("/");
        if (pathParts.length <= 2 && pathParts[1].equals("epic")) {
            if (manager.printAllEpics().isEmpty()) {
                sendMassage(httpExchange, 404, "Отсутствуют эпики для удаления.");
            } else {
                manager.removeAllEpics();
                sendMassage(httpExchange, 200, "Эпики успешно удалены.");
            }
        } else if (pathParts.length > 2 && pathParts[1].equals("epic")) {
            int id = Integer.parseInt(pathParts[2]);
            Epic epic = manager.getEpicById(id);
            if (epic == null) {
                sendMassage(httpExchange, 404, "Эпик c ID " + id + " отсутствует.");
            } else {
                manager.removeEpicById(epic.getId());
                sendMassage(httpExchange, 200, "Задача успешно удалена.");
            }
        } else {
            sendMassage(httpExchange, 500, "Указанный путь " + pathParts[1] + " не корректен.");
        }
    }
}
