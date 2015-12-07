package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import static main.Main.*;

public class ClientConnect implements Runnable {

    int portNumber = 25565;

    public static ArrayList<ClientSocket> sockets;

    public ClientConnect() {

        sockets = new ArrayList<>();

        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
            while (true) {
                Main.pushToLog("Lisening for Client connection");

                Socket socket = serverSocket.accept();
                sockets.add(new ClientSocket(socket));
                Main.pushToLog("Client connection established on IP:" + socket.getInetAddress());
                area2.setText("Sockets: " + sockets.size());
            }
        } catch (IOException ex) {
        }
    }
}
