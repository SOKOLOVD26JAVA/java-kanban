package main.ru.yandex.practicum.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.ru.yandex.practicum.manager.TaskManager;
import main.ru.yandex.practicum.model.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistroyHandler extends BaseHttpHandler {

    public HistroyHandler(TaskManager manager) {
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
        if(manager.getHistory().isEmpty()){
            sendMassage(httpExchange,404,"История просмотра пуста");
        }else {
            List<Task> history = manager.getHistory();
            String historySend = gson.toJson(history);
            sendJsonResponse(httpExchange,historySend);
        }

    }
}
