package com.wh1lec0d3r_.bunchsk.core.server;

import com.wh1lec0d3r_.bunchsk.core.api.utils.Hash;
import com.wh1lec0d3r_.bunchsk.core.server.packet.PacketManager;
import com.wh1lec0d3r_.bunchsk.core.server.packet.YPacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private CoreServer coreServer;

    private String name;
    private boolean enabled;

    ClientHandler(Socket socket, CoreServer coreServer) {

        System.out.println("█ Client » Creating");

        //vars
        this.socket = socket;
        this.coreServer = coreServer;

        System.out.println("█ Client » Authorizing");
        this.authorize();
    }

    private void authorize() {

        DataOutputStream dataOutputStream;
        DataInputStream dataInputStream;

        try {
            dataInputStream = new DataInputStream(this.getSocket().getInputStream());
            dataOutputStream = new DataOutputStream(this.getSocket().getOutputStream());

            String name = dataInputStream.readUTF();
            String password = dataInputStream.readUTF();
            int hashId = dataInputStream.readInt();

            this.name = name;
            boolean auth = Hash.validate(Hash.getEnumById(hashId), password, this.getCoreServer().getConfigData().getPassword());

            if(auth) {

                System.out.println("█ Client » " + this.getHandlerName() + " » Authorized");
                System.out.println("█ Client » " + this.getHandlerName() + " » Starting » Thread » Reading data");
                this.start();

                dataOutputStream.writeBoolean(true);
                dataOutputStream.flush();

            } else {

                System.out.println("█ Client » " + this.getHandlerName() + " » Error on authorize: Password don't match");

                dataOutputStream.writeBoolean(false);
                dataOutputStream.flush();
            }

        } catch (IOException ex) {

            System.out.println("█ Client » " + this.getHandlerName() + " » Error on authorize: " + ex.getMessage());
        }
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

                System.out.println("█ Client » " + this.getHandlerName() + " » Error on reading packet: " + e.getMessage());
                this.getCoreServer().removeClientHandler(this);
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public CoreServer getCoreServer() {
        return coreServer;
    }

    public String getHandlerName() {
        return name;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
