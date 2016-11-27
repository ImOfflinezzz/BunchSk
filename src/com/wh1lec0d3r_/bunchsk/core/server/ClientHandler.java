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

    public ClientHandler(Socket socket, CoreServer coreServer) {
        System.out.println("Creating client");
        this.socket = socket;
        this.coreServer = coreServer;
        System.out.println("Created client");
        System.out.println("Starting read data");
        //this.start();
        this.readData();
    }

    private void readData() {
        try {
            DataInputStream dataInputStream = new DataInputStream(this.getSocket().getInputStream());
            String name = dataInputStream.readUTF();
            String password = dataInputStream.readUTF();
            int hashId = dataInputStream.readInt();

            this.name = name;
            if(Hash.validate(Hash.getEnumById(hashId), password, this.getCoreServer().getConfigData().password))
                this.start();
            else {
                System.out.println("logout");
                this.getCoreServer().removeClient(this);
                this.getSocket().close();
            }



        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public void run() {
        System.out.println("succesefully");
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
                this.stop();
                this.coreServer.removeClient(this);
                e.printStackTrace();
            }
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public CoreServer getCoreServer() {
        return coreServer;
    }
}
