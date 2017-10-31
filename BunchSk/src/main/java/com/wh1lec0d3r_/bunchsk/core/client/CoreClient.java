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

        System.out.println("█ Client » Loading");

        this.configData = configData;
        System.out.println("█ Client » Connecting to server socket");
        this.openSocket(configData.getHost(), configData.getPort());

        System.out.println("█ Client » Authorizing");
        this.authorize();
    }

    private void authorize() {
        DataInputStream dataInputStream;
        DataOutputStream dataOutputStream;
        try {

            dataInputStream = new DataInputStream(this.getSocket().getInputStream());
            dataOutputStream = new DataOutputStream(this.getSocket().getOutputStream());

            dataOutputStream.writeUTF(Bukkit.getWorldContainer().getCanonicalFile().getName());
            dataOutputStream.writeUTF(this.getConfigData().getPassword());
            dataOutputStream.writeInt(this.getConfigData().getHashId());

            boolean auth = dataInputStream.readBoolean();

            if(auth) {
                System.out.println("█ Client » Authorized");
                System.out.println("█ Client » Starting » Thread » Reading data");
                this.start();

            } else {
                System.out.println("█ Client » Error on authorize");
            }


        } catch (IOException ex) {

            System.out.println("█ Client Error on authorize: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void openSocket(String host, int port) {

        try {

            this.socket = new Socket(host, port);
            System.out.println("█ Client » Connected to socket");
        } catch (IOException e) {

            System.out.println("█ Client » Error on connecting to socket: " + e.getMessage());
        }
    }

    public void run() {

        while (!this.getSocket().isClosed()) {

            try {

                DataInputStream dataInputStream = new DataInputStream(this.getSocket().getInputStream());
                short id = dataInputStream.readShort();

                YPacket yPacket = PacketManager.getPacket(id);

                if(yPacket != null) {

                    yPacket.read(dataInputStream);
                    yPacket.handle();
                }
                Thread.sleep(10L);

            } catch (IOException | InterruptedException ex) {

                try {

                    this.getSocket().close();
                } catch (IOException e) {

                    System.out.println("█ Client » Error on reading data: " + e.getMessage());
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
