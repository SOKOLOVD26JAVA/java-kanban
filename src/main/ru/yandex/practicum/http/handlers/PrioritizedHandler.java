package main.ru.yandex.practicum.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Task;

import java.io.IOException;
import java.util.List;
import java.util.TreeSet;

public class PrioritizedHandler extends BaseHttpHandler {

    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();
        switch (method) {
            case "GET" -> get(httpExchange);
            default -> sendMassage(httpExchange, 500, "Ошибка обработки запроса " + method);
        }
    }

    private void get(HttpExchange httpExchange) throws IOException {
        if (manager.getPrioritizedTasks().isEmpty()) {
            sendMassage(httpExchange, 404, "Отсортированный список пуст");
        } else {
            TreeSet<Task> PrioritizedTasks = manager.getPrioritizedTasks();
            String historySend = gson.toJson(PrioritizedTasks);
            sendJsonResponse(httpExchange, historySend);
        }

    }
}
