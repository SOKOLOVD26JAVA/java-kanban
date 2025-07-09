package main.ru.yandex.practicum.http.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import main.ru.yandex.practicum.http.adapters.DurationAdapter;
import main.ru.yandex.practicum.http.adapters.LocalDateTimeAdapter;
import main.ru.yandex.practicum.manager.TaskManager;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;


public abstract class BaseHttpHandler implements HttpHandler {
    TaskManager manager;
    Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    @Override
    public abstract  void handle(HttpExchange httpExchange) throws IOException;

    protected void sendMassage(HttpExchange httpExchange, int statusCode, String text) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        httpExchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        httpExchange.sendResponseHeaders(statusCode, resp.length);
        httpExchange.getResponseBody().write(resp);
        httpExchange.close();
    }

    protected void sendJsonResponse(HttpExchange exchange, String json) throws IOException {
        byte[] bytes = json.getBytes();
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(200, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }


}
