package com.wh1lec0d3r_.bunchsk.core.client;

import com.wh1lec0d3r_.bunchsk.core.client.config.ConfigData;
import com.wh1lec0d3r_.bunchsk.core.client.packet.PacketManager;
import com.wh1lec0d3r_.bunchsk.core.client.packet.YPacket;
import org.bukkit.Bukkit;

import java.io.DataInputStream;
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
        this.readData();
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

    private void readData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(this.getSocket().getInputStream());
            if(dataInputStream.readBoolean()) {
                this.start();
                System.out.println("logged in");
            } else {
                System.out.println("i am closed :c");
                this.getSocket().close();
            }

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
        while (!this.getSocket().isClosed()) {
            try {
                DataInputStream dataInputStream = new DataInputStream(this.getSocket().getInputStream());
                short id = dataInputStream.readShort();

                YPacket yPacket = PacketManager.getPacket(id);
                if(yPacket != null) {
                    //yPacket.setSocket(this.getSocket());
                    //yPacket.setClientHandler(this);
                    yPacket.read(dataInputStream);
                    yPacket.handle();
                }
                Thread.sleep(10L);
            } catch (IOException | InterruptedException ex) {
                try {
                    this.getSocket().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                this.stop();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public ConfigData getConfigData() {
        return configData;
    }
}
