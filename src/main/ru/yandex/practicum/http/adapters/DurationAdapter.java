package main.ru.yandex.practicum.http.adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;


public class DurationAdapter extends TypeAdapter<Duration> {
    public void write(final JsonWriter jsonWriter, final Duration duration) throws IOException {
        if(duration == null){
            jsonWriter.nullValue();
        }else {
            jsonWriter.value(String.valueOf(duration));
        }
    }

    public Duration read(final JsonReader jsonReader) throws IOException {
        return Duration.parse(jsonReader.nextString());
    }
}
