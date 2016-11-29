package com.wh1lec0d3r_.bunchsk.core.server;

import com.wh1lec0d3r_.bunchsk.core.server.config.ConfigData;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

public class CoreServer {

    static CoreServer instance;

    private File configFile = new File("config.json");

    private ConfigData configData;
    private ServerSocket serverSocket;
    private Map<Socket, ClientHandler> clients;
    private HashMap<String, Object> vars = new HashMap<>();

    public Thread readConnectionsThread;
    public Thread readConsoleThread;
    private String value = "none";

    //test comment, for test push, test
    public CoreServer(String[] args) {

        instance = this;

        this.clients = new HashMap<>();

        System.out.println("Loading...");
        System.out.println("Loading config...");
        this.loadConfig();
        System.out.println("Opening socket...");
        this.openSocket(this.getConfigData().port);
        System.out.println("Starting read connections...");
        this.startReadConnections();
        //System.out.println("test :/");
        this.startReadConsole();
        System.out.println("console reader started");
    }



    private void openSocket(int port) {
        this.value = "opened";
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("Socket opened successfully");
        } catch (IOException e) {
            System.out.println("Error on opening socket...");
            e.printStackTrace();
        }
    }

    private void startReadConnections() {

        this.readConnectionsThread = new Thread(() -> {
           while (!this.getServerSocket().isClosed()) {
               try {
                   Socket socket = this.getServerSocket().accept();

                   System.out.println("reading connection");

                   ClientHandler clientHandler = new ClientHandler(socket, this);

                   this.clients.put(socket, clientHandler);

                   //this.clients.put(socket, new ClientHandler(socket, instance));

                   Thread.sleep(10L);
               } catch (IOException | InterruptedException ex) {
                   ex.printStackTrace();
               }
           }
        });

        this.readConnectionsThread.start();
    }

    private void startReadConsole() {
        this.readConsoleThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                String line = scanner.nextLine();
                if(line.length() != 0) {
                    if(line.startsWith("/")) {
                        switch (line) {
                            case "/end":
                                System.exit(0);
                                break;
                            case "/servers":
                                int clientCount = 0;
                                System.out.println("CLIENTS COUNT: " + this.getClients().size());
                                //System.out.println("HASH: " + this.getClients().toString());
                                for(ClientHandler clientHandler : this.getClients().values()) {
                                    if(!clientHandler.isEnabled())
                                        continue;
                                    System.out.println("Clients: ");
                                    System.out.println("    Client #" + clientCount + " Client name: " + clientHandler.getHandlerName() + " Client ip: " + clientHandler.getSocket().getInetAddress().getHostAddress());
                                    //this.removeClientHandler(clientHandler);
                                    clientCount++;
                                }

                                if(this.clients.size() == 0)
                                    System.out.println("You are alone :(");
                                break;
                            case "saveConfig":
                                this.configData.saveConfig();
                                break;
                            default:
                                System.out.println("Unknown command");
                                break;
                        }
                    }
                }
                //System.out.println(line);
            }
        });

        this.readConsoleThread.start();
    }

    private void loadConfig() {

        if (this.getConfigFile().exists()) {
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

    public Map<Socket, ClientHandler> getClients() {
        return this.clients;
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

    public void removeClientHandler(ClientHandler clientHandler) {
        //System.out.println("HASH: " + this.getClients().toString());
        System.out.println("Clients: " + this.clients.size());
        System.out.println("value: " + this.value);

        clientHandler.setEnabled(false);

        try {
            clientHandler.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(clientHandler.isAlive())
            clientHandler.stop();

        System.out.println("Clients: " + this.clients.size());
    }

    public File getConfigFile() {
        return configFile;
    }

    public static CoreServer getInstance() {
        return instance;
    }
}
