package com.offline.bunchsk.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


public class DocsGenerator {
    public static void createDocs() throws IOException{
        File file = new File("./plugins/BunchSk/docs.txt");
        if(file.exists()){
            file.delete();}
        file.getParentFile().mkdirs();
        file.createNewFile();
    }
    public static void writeDocs(String name, String[] syntax, String type) throws IOException{
        Object sp = System.lineSeparator();
        StringBuilder builder = new StringBuilder();
        for(String s : syntax) {
            if(syntax.length>1){
                builder.append(s+" , ");
            } else {
                builder.append(s);
            }
        }
        String s = "Name: "+name+sp+"Syntax: "+builder.toString()+sp+"Type: "+type+sp+" "+sp;
        byte data[] = s.getBytes();
        Path p = Paths.get("plugins/BunchSk/docs.txt");
        try (OutputStream out = new BufferedOutputStream(
        Files.newOutputStream(p, CREATE, APPEND))) {
        out.write(data, 0, data.length);
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}
