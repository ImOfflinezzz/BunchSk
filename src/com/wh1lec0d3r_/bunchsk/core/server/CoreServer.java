package com.wh1lec0d3r_.bunchsk.core.server;

import com.wh1lec0d3r_.bunchsk.core.server.config.ConfigData;

import java.io.IOException;
import java.net.ServerSocket;

public class CoreServer {

    private ConfigData configData;
    private ServerSocket serverSocket;

    public CoreServer(String[] args) {
        this.loadConfig();
        this.configData.vars.put("online", 1);
        this.configData.vars.put("username", "hello");
        this.configData.saveConfig();
        this.openSocket(this.getConfigData().port);
    }

    private void openSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {

        if (ConfigData.configFile.exists()) {
            configData = new ConfigData();
            configData = configData.readConfig(ConfigData.class);
        } else {
            configData = new ConfigData();
            configData.saveConfig();
        }
    }

    public ConfigData getConfigData() {
        return configData;
    }
}
