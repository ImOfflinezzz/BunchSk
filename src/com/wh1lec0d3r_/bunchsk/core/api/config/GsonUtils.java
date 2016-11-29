package com.wh1lec0d3r_.bunchsk.core.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonUtils {

    private static Gson gson;

    public static Gson getGson() {

        if(gson == null)
            gson = getGsonBuilder().create();

        return gson;
    }

    private static GsonBuilder getGsonBuilder() {

        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation()
                .registerTypeAdapter(JsonSerializable.class, new PropertyBasesInterfaceMarshal());
    }

}
