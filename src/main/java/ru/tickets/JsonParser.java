package ru.tickets;

import com.google.gson.Gson;

import java.io.Reader;
import java.lang.reflect.Type;

public class JsonParser {
    private static final Gson GSON = new Gson();

    public static Tickets parse(Reader reader, Type type) {
        return GSON.fromJson(reader, type);
    }
}
