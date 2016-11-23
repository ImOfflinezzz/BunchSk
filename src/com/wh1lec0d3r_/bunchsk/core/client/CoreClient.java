package com.wh1lec0d3r_.bunchsk.core.client;

import com.wh1lec0d3r_.bunchsk.core.client.config.ConfigData;

import java.io.IOException;
import java.net.Socket;

public class CoreClient extends Thread {

    private ConfigData configData;
    private Socket socket;

    public CoreClient(ConfigData configData) {
        System.out.println("Loading...");
        this.configData = configData;
        this.openSocket(configData.host, configData.port);
    }

    private void openSocket(String host, int port) {

        try {
            this.socket = new Socket(host, port);
            System.out.println("socket opened");
        } catch (IOException e) {
            System.out.println("error on opening socket");
            e.printStackTrace();
        }
    }

    public void run() {

    }
}
