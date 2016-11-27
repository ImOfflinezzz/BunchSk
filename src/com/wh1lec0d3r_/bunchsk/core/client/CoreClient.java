package com.wh1lec0d3r_.bunchsk.core.client;

import com.wh1lec0d3r_.bunchsk.core.api.utils.Hash;
import com.wh1lec0d3r_.bunchsk.core.client.config.ConfigData;
import org.bukkit.Bukkit;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class CoreClient extends Thread {

    private ConfigData configData;
    private Socket socket;

    public CoreClient(ConfigData configData) {
        System.out.println("Loading...");
        this.configData = configData;
        this.openSocket(configData.host, configData.port);
        this.sendData();
    }

    private void sendData() {
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(this.getSocket().getOutputStream());
            dataOutputStream.writeUTF(Bukkit.getWorldContainer().getCanonicalFile().getName());
            dataOutputStream.writeUTF(this.getConfigData().password);
            dataOutputStream.writeInt(this.getConfigData().hashId);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    public Socket getSocket() {
        return socket;
    }

    public ConfigData getConfigData() {
        return configData;
    }
}
