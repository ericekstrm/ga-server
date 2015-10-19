package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket implements Runnable {

    int portNumber = 25565;

    ArrayList<Socket> sockets;
    ArrayList<BufferedReader> ins;
    ArrayList<PrintWriter> outs;

    public ClientSocket() {

        sockets = new ArrayList<>();
        ins = new ArrayList<>();
        outs = new ArrayList<>();

        Thread th = new Thread(this);
        th.start();
        new Messages();
    }

    @Override
    public void run() {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(portNumber);
            while (true) {
                Main.puchToLog("Lisening for Client connection");

                Socket socket = serverSocket.accept();
                sockets.add(socket);
                Main.puchToLog("Client connection established on IP:" + socket.getInetAddress());
                outs.add(new PrintWriter(socket.getOutputStream(), true));
                ins.add(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            }
        } catch (IOException ex) {
        }
    }

    private class Messages implements Runnable {

        public Messages() {
            Thread th = new Thread(this);
            th.start();
        }

        @Override
        public void run() {
            getMessages();
        }

        public void getMessages() {
            while (true) {
                for (BufferedReader br : ins) {
                    try {
                        new Reader();
                        Main.message = br.readLine();
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }
}
