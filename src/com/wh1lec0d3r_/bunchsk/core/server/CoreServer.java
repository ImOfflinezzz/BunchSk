package com.wh1lec0d3r_.bunchsk.core.server;

import com.wh1lec0d3r_.bunchsk.core.server.config.ConfigData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class CoreServer {

    private ConfigData configData;
    private ServerSocket serverSocket;
    private HashMap<Socket, ClientHandler> clients = new HashMap<>();
    private HashMap<String, Object> vars = new HashMap<>();

    public CoreServer(String[] args) {
        System.out.println("Loading...");
        System.out.println("Loading config...");
        this.loadConfig();
        System.out.println("Opening socket...");
        this.openSocket(this.getConfigData().port);
        System.out.println("Starting read connections...");
        this.startReadConnections();
    }

    private void openSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Socket opened successfully");
        } catch (IOException e) {
            System.out.println("Error on opening socket...");
            e.printStackTrace();
        }
    }

    private void startReadConnections() {

        while (!this.getServerSocket().isClosed()) {
            try {
                Socket socket = this.getServerSocket().accept();

                System.out.println("Reading connection");

                this.clients.put(socket, new ClientHandler(socket));

                Thread.sleep(10L);
            } catch (IOException | InterruptedException e) {
                System.out.println("Error on read connection");
                e.printStackTrace();
            }
        }
    }

    private void loadConfig() {

        if (ConfigData.configFile.exists()) {
            System.out.println("Loading from file...");
            configData = new ConfigData();
            configData = configData.readConfig(ConfigData.class);
        } else {
            System.out.println("Creating file...");
            configData = new ConfigData();
            configData.saveConfig();
        }
        System.out.println("File loaded successfully");
    }

    public ConfigData getConfigData() {
        return configData;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public HashMap<Socket, ClientHandler> getClients() {
        return clients;
    }

    public ClientHandler getClient(Socket socket) {
        return this.getClients().get(socket);
    }

    public HashMap<String, Object> getVars() {
        return vars;
    }

    public Object getVar(String key) {
        return this.getVars().get(key);
    }

    public void setVar(String key, Object value) {
        this.getVars().put(key, value);
    }

    public HashMap<String, Object> getConfigVars() {
        return this.getConfigData().vars;
    }

    public Object getConfigVar(String key) {
        return this.getConfigVars().get(key);
    }

    public void setConfigVar(String key, Object value) {
        this.getConfigVars().put(key, value);
    }

    public void removeClient(ClientHandler clientHandler) {
        Iterator<Socket> clientsIterator = this.getClients().keySet().iterator();

        while (clientsIterator.hasNext()) {
            Socket socket = clientsIterator.next();
            this.getClients().remove(socket);
        }
    }

    public void removeClient(Socket socket) {
        this.getClients().remove(socket);
    }
}
