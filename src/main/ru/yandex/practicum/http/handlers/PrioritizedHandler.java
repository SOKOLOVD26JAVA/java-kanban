package main.ru.yandex.practicum.http.handlers;

import com.sun.net.httpserver.HttpExchange;
import main.ru.yandex.practicum.manager.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {

    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

    }
}
