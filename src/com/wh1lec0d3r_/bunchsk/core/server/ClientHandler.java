package com.wh1lec0d3r_.bunchsk.core.server;

import com.wh1lec0d3r_.bunchsk.core.packet.PacketManager;
import com.wh1lec0d3r_.bunchsk.core.packet.YPacket;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;

    public ClientHandler(Socket socket) {
        System.out.println("Created client");
        this.socket = socket;
        System.out.println("Starting read data");
        this.start();
    }

    public void run() {
        while (!this.getSocket().isClosed()) {
            try {
                DataInputStream dataInputStream = new DataInputStream(this.getSocket().getInputStream());
                short id = dataInputStream.readShort();

                YPacket yPacket = PacketManager.getPacket(id);
                if(yPacket != null) {
                    yPacket.setSocket(this.getSocket());
                    yPacket.setClientHandler(this);
                    yPacket.read(dataInputStream);
                    yPacket.handle();
                }


                Thread.sleep(10L);
            } catch (IOException | InterruptedException e) {
                System.out.println("Error on read data");
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
