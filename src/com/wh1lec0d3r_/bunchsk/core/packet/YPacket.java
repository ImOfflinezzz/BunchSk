package com.wh1lec0d3r_.bunchsk.core.packet;

import com.wh1lec0d3r_.bunchsk.core.server.ClientHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public abstract class YPacket {

    private Socket socket;
    private ClientHandler clientHandler;

    public Socket getSocket() {
        return socket;
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public void setClientHandler(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public abstract short getId();

    public abstract void read(DataInputStream dataInputStream) throws IOException;

    public abstract void write(DataOutputStream dataOutputStream) throws IOException;

    public abstract void handle();
}
