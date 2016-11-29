package com.wh1lec0d3r_.bunchsk.core.server;

import com.wh1lec0d3r_.bunchsk.core.server.config.ConfigData;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class CoreServer {

    private  static CoreServer coreServer;

    private File configFile = new File("config.json");

    private ConfigData configData;
    private ServerSocket serverSocket;
    private Map<Socket, ClientHandler> clients;
    private HashMap<String, Object> vars;

    public CoreServer() {

        System.out.println("");

        System.out.println("████████████████████████████████████");
        System.out.println("█────██─█─█─██─█────█─██─██───█─██─█");
        System.out.println("█─██──█─█─█──█─█─██─█─██─██─███─█─██");
        System.out.println("█────██─█─█─█──█─████────██───█──███");
        System.out.println("█─██──█─█─█─██─█─██─█─██─████─█─█─██");
        System.out.println("█────██───█─██─█────█─██─██───█─██─█");
        System.out.println("████████████████████████████████████");

        System.out.println("");

        //vars
        coreServer = this;

        this.clients = new HashMap<>();
        this.vars = new HashMap<>();

        //config
        System.out.println("█ Loading » Config");
        this.loadConfig();

        //opening socket
        System.out.println("█ Opening » ServerSocket");
        this.openSocket(this.getConfigData().getPort());

        //Starting read connections thread
        System.out.println("█ Starting » Thread » Read connections");
        this.startReadConnections();

        //Starting read console thread
        System.out.println("█ Starting » Thread » Read console");
        this.startReadConsole();
    }



    private void openSocket(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            System.out.println("█ Socket » Server successefully opened!");
        } catch (IOException e) {
            System.out.println("█ Socket » Error on opening server.");
            e.printStackTrace();
        }
    }

    private void startReadConnections() {

        Thread readConnectionsThread = new Thread(() -> {

            while (!this.getServerSocket().isClosed()) {

                try {

                    Socket socket = this.getServerSocket().accept();
                    System.out.println("█ Client » New client connected");

                    this.clients.put(socket, new ClientHandler(socket, this));

                    Thread.sleep(10L);

                } catch (IOException | InterruptedException ex) {

                    ex.printStackTrace();
                }
            }
        });

        readConnectionsThread.start();
    }

    private void startReadConsole() {

        Thread readConsoleThread = new Thread(() -> {

            Scanner scanner = new Scanner(System.in);

            while (scanner.hasNext()) {

                String line = scanner.nextLine();
                String[] args = line.split(" ");

                if (args.length == 0)
                    continue;

                switch (args[0].toLowerCase()) {
                    case "help":
                        System.out.println("█ Command » Help command:");
                        System.out.println("█   » Help - 'help'");
                        System.out.println("█   » Shutdown core - 'end'");
                        System.out.println("█   » List of clients - 'clients'");
                        break;
                    case "end":
                        System.out.println("█ Command » Shutdown core!");
                        System.exit(0);
                        break;
                    case "clients":
                        if(args.length == 1) {
                            System.out.println("█ Command » Help 'clients' command: ");
                            System.out.println("█   » All clients - 'clients all'");
                            System.out.println("█   » Connected clients - 'clients connected'");
                            System.out.println("█   » Closed clients - 'clients closed'");
                        }

                        if(args.length > 1) {
                            switch (args[1].toLowerCase()) {
                                case "all":
                                    System.out.println("█ Command » Client all:");
                                    System.out.println("█   » Clients: " + this.getClients().size());
                                    for(ClientHandler clientHandler : this.getClients().values()) {
                                        System.out.println("█   » Client name: " + clientHandler.getHandlerName() + " Client IP: " + clientHandler.getSocket().getInetAddress().getHostAddress());
                                    }
                                    break;
                                case "connected":
                                    List<ClientHandler> connected = new LinkedList<>();

                                    this.getClients().entrySet().stream().filter(socketClientHandlerEntry -> socketClientHandlerEntry.getValue().isEnabled()).forEach(socketClientHandlerEntry -> connected.add(socketClientHandlerEntry.getValue()));

                                    System.out.println("█ Command » Client connected:");
                                    System.out.println("█   » Clients: " + connected.size());
                                    for(ClientHandler clientHandler : connected) {
                                        System.out.println("█   » Client name: " + clientHandler.getHandlerName() + " Client IP: " + clientHandler.getSocket().getInetAddress().getHostAddress());
                                    }
                                    break;
                                case "closed":
                                    List<ClientHandler> closed = new LinkedList<>();

                                    this.getClients().entrySet().stream().filter(socketClientHandlerEntry -> !socketClientHandlerEntry.getValue().isEnabled()).forEach(socketClientHandlerEntry -> closed.add(socketClientHandlerEntry.getValue()));

                                    System.out.println("█ Command » Client connected:");
                                    System.out.println("█   » Clients: " + closed.size());
                                    for(ClientHandler clientHandler : closed) {
                                        System.out.println("█   » Client name: " + clientHandler.getName() + " Client IP: " + clientHandler.getSocket().getInetAddress().getHostAddress());
                                    }
                                    break;
                                default:
                                    System.out.println("█ Command » Help 'clients' command: ");
                                    System.out.println("█   » All clients - 'clients all'");
                                    System.out.println("█   » Connected clients - 'clients connected'");
                                    System.out.println("█   » Closed clients - 'clients closed'");
                                    break;
                            }
                        }

                        break;
                    default:
                        System.out.println("█ Command » Unknown command, type 'help' for help!");
                        break;
                }
            }
        });

        readConsoleThread.start();
    }

    private void loadConfig() {

        if (this.getConfigFile().exists()) {
            System.out.println("█ Config » Loading data from config file");
            configData = new ConfigData();
            configData = configData.readConfig(ConfigData.class);
        } else {
            System.out.println("█ Config » Creating config file");
            configData = new ConfigData();
            configData.saveConfig();
        }
        System.out.println("█ Config » Config loaded successefully");
    }

    public ConfigData getConfigData() {
        return configData;
    }

    private ServerSocket getServerSocket() {
        return serverSocket;
    }

    private Map<Socket, ClientHandler> getClients() {
        return this.clients;
    }

    public ClientHandler getClient(Socket socket) {
        return this.getClients().get(socket);
    }

    private HashMap<String, Object> getVars() {
        return vars;
    }

    public Object getVar(String key) {
        return this.getVars().get(key);
    }

    public void setVar(String key, Object value) {
        this.getVars().put(key, value);
    }

    private HashMap<String, Object> getConfigVars() {
        return this.getConfigData().getVars();
    }

    public Object getConfigVar(String key) {
        return this.getConfigVars().get(key);
    }

    public void setConfigVar(String key, Object value) {
        this.getConfigVars().put(key, value);
    }

    public void removeClientHandler(ClientHandler clientHandler) {

        clientHandler.setEnabled(false);

        try {

            clientHandler.getSocket().close();
        } catch (IOException e) {

            e.printStackTrace();
        }

        if(clientHandler.isAlive())
            clientHandler.stop();

    }

    public File getConfigFile() {
        return this.configFile;
    }

    public static CoreServer getCoreServer() {
        return coreServer;
    }
}
