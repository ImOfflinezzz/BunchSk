package com.wh1lec0d3r_.bunchsk.core.server.config;

import com.google.gson.annotations.Expose;
import com.wh1lec0d3r_.bunchsk.core.api.config.GsonUtils;
import com.wh1lec0d3r_.bunchsk.core.api.config.JsonSerializable;
import com.wh1lec0d3r_.bunchsk.core.api.utils.IOUtils;
import com.wh1lec0d3r_.bunchsk.core.server.CoreServer;

import java.io.File;
import java.util.HashMap;

public class ConfigData implements JsonSerializable {

    //file
    //public static File configFile = new File("config.json");

    //vars
    @Expose
    public String
        password = "password";

    @Expose
    public int
        port = 6000;

    @Expose
    public HashMap<String, Object> vars = new HashMap<>();


    //utils
    public ConfigData readConfig(Class classOfT) {
        return (ConfigData) IOUtils.readFromFile(CoreServer.getInstance().getConfigFile(), classOfT, GsonUtils.getGson());
    }

    public void saveConfig() {
        IOUtils.writeToFile(CoreServer.getInstance().getConfigFile(), this, GsonUtils.getGson());
    }
}
