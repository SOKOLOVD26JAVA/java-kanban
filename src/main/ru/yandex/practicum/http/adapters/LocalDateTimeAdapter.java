package main.ru.yandex.practicum.http.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
        if(localDateTime == null){
            jsonWriter.nullValue();
        }else {
            jsonWriter.value(localDateTime.format(format));
        }
    }

    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        return LocalDateTime.parse(jsonReader.nextString(), format);
    }

}
