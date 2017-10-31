package com.wh1lec0d3r_.bunchsk.core.client.config;

import com.google.gson.annotations.Expose;
import com.offline.bunchsk.BunchSk;
import com.wh1lec0d3r_.bunchsk.core.api.config.GsonUtils;
import com.wh1lec0d3r_.bunchsk.core.api.config.JsonSerializable;
import com.wh1lec0d3r_.bunchsk.core.api.utils.IOUtils;

public class ConfigData implements JsonSerializable {

    //vars
    @Expose
    private String
        host = "localhost",
        password = "password";

    @Expose
    private int
        port = 6000,
        hashId = 1;

    public ConfigData readConfig(Class<ConfigData> classOfT) {
        return (ConfigData) IOUtils.readFromFile(BunchSk.getBunchSk().getConfigFile(), classOfT, GsonUtils.getGson());
    }

    public void saveConfig() {
        IOUtils.writeToFile(BunchSk.getBunchSk().getConfigFile(), this, GsonUtils.getGson());
    }

    public String getPassword() {
        return password;
    }

    public int getPort() {
        return port;
    }

    public int getHashId() {
        return hashId;
    }

    public String getHost() {
        return host;
    }
}
