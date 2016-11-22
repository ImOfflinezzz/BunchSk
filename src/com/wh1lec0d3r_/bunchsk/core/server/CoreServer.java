package com.wh1lec0d3r_.bunchsk.core.server;

import com.wh1lec0d3r_.bunchsk.core.server.config.ConfigData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class CoreServer {

    private ConfigData configData;
    private ServerSocket serverSocket;
    private HashMap<Socket, ClientHandler> clients = new HashMap<>();

    public CoreServer(String[] args) {
        this.loadConfig();
        this.getConfigData().saveConfig();
        this.openSocket(this.getConfigData().port);
        this.startReadConnections();
    }

    private void openSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReadConnections() {

        while (!this.getServerSocket().isClosed()) {
            try {
                Socket socket = this.getServerSocket().accept();

                this.clients.put(socket, new ClientHandler(socket));

                Thread.sleep(10L);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
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

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
}
