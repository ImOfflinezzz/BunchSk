package com.wh1lec0d3r_.bunchsk.core.client.config;

import com.google.gson.annotations.Expose;
import com.offline.bunchsk.BunchSk;
import com.wh1lec0d3r_.bunchsk.core.api.config.GsonUtils;
import com.wh1lec0d3r_.bunchsk.core.api.config.JsonSerializable;
import com.wh1lec0d3r_.bunchsk.core.api.utils.IOUtils;

import java.io.File;
import java.util.HashMap;

public class ConfigData implements JsonSerializable {

    //vars
    @Expose
    public String
        host = "localhost",
        password = "password";

    @Expose
    public int
        port = 6000,
        hashId = 1;



    //utils
    public ConfigData readConfig(Class classOfT) {
        return (ConfigData) IOUtils.readFromFile(BunchSk.getBunchSk().getConfigFile(), classOfT, GsonUtils.getGson());
    }

    public void saveConfig() {
        IOUtils.writeToFile(BunchSk.getBunchSk().getConfigFile(), this, GsonUtils.getGson());
    }
}
