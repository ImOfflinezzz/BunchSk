package com.wh1lec0d3r_.bunchsk.core.api.config;

import com.google.gson.*;

import java.lang.reflect.Type;

public class PropertyBasesInterfaceMarshal implements JsonSerializer<Object>, JsonDeserializer<Object> {

    private static final String CLASS_META_KEY = "j_class";

    public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {

        JsonElement jsonElement = jsonSerializationContext.serialize(object, object.getClass());
        jsonElement.getAsJsonObject().addProperty(CLASS_META_KEY, object.getClass().getCanonicalName());

        return jsonElement;
    }

    public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        String className = jsonObject.get(CLASS_META_KEY).getAsString();

        try {

            Class clazz = Class.forName(className);
            return jsonDeserializationContext.deserialize(jsonElement, clazz);

        } catch (ClassNotFoundException ex) {

            throw new JsonParseException(ex);
        }
    }

}
