package com.wh1lec0d3r_.bunchsk.core.server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;

        //yep, just read. I have no idea for name this function..
        this.read();
    }

    private void read() {
        while (!this.getSocket().isClosed()) {
            try {
                DataInputStream dataInputStream = new DataInputStream(this.getSocket().getInputStream());

                Thread.sleep(10L);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

}
