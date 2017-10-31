package com.wh1lec0d3r_.bunchsk.core.server.config;

import com.google.gson.annotations.Expose;
import com.wh1lec0d3r_.bunchsk.core.api.config.GsonUtils;
import com.wh1lec0d3r_.bunchsk.core.api.config.JsonSerializable;
import com.wh1lec0d3r_.bunchsk.core.api.utils.IOUtils;
import com.wh1lec0d3r_.bunchsk.core.server.CoreServer;

import java.util.HashMap;

public class ConfigData implements JsonSerializable {

    //vars
    @Expose
    private String
        password = "password";

    @Expose
    private int
        port = 6000;

    @Expose
    private HashMap<String, Object> vars = new HashMap<>();


    public ConfigData readConfig(Class<ConfigData> classOfT) {
        return (ConfigData) IOUtils.readFromFile(CoreServer.getCoreServer().getConfigFile(), classOfT, GsonUtils.getGson());
    }

    public void saveConfig() {
        IOUtils.writeToFile(CoreServer.getCoreServer().getConfigFile(), this, GsonUtils.getGson());
    }

    public int getPort() {
        return port;
    }

    public String getPassword() {
        return password;
    }

    public HashMap<String, Object> getVars() {
        return vars;
    }
}
