package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientConnect implements Runnable {

    int portNumber = 25565;

    ArrayList<ClientSocket> sockets;
    ArrayList<PrintWriter> outs;

    public ClientConnect() {

        sockets = new ArrayList<>();

        Thread th = new Thread(this);
        th.start();
        
        
        while (true) {
            if (sockets.isEmpty()) {
                Main.message = "128 128 128";
            }
        }
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
            while (true) {
                Main.pushToLog("Lisening for Client connection");

                Socket socket = serverSocket.accept();
                sockets.add(new ClientSocket(socket,sockets));
                Main.pushToLog("Client connection established on IP:" + socket.getInetAddress());
            }
        } catch (IOException ex) {
        }
    }
}
