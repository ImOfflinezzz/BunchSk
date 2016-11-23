package com.wh1lec0d3r_.bunchsk.core.api.utils;

import com.google.gson.Gson;
import com.wh1lec0d3r_.bunchsk.core.api.config.JsonSerializable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by wh1leC0d3r_ on 22.11.2016.
 */
public class IOUtils {

    public static <T> Object readFromFile(File file, Class<T> classOfT, Gson gson)
    {
        List<String> allString = null;
        try
        {
            allString = Files.readAllLines(Paths.get(file.getAbsolutePath(), new String[0]), Charset.defaultCharset());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (String s : allString)
        {
            sb.append(s).append("\n");
        }

        return gson.fromJson(sb.toString(), classOfT);
    }

    public static void writeToFile(File file, Object object, Gson gson)
    {
        String json = gson.toJson(object, JsonSerializable.class);
        try
        {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.print(json);
            printWriter.flush();
            printWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
