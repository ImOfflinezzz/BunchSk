package com.wh1lec0d3r_.bunchsk.core.server;

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
