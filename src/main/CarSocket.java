package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CarSocket implements Runnable {

    int portNumber = 25566;

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public CarSocket() {
        Thread th = new Thread(this);
        th.start();
        sendMessages();
    }

    @Override
    public void run() {
        Main.puchToLog("Lisening for Car connection");
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            socket = serverSocket.accept();
            Main.puchToLog("Car connection established on IP:" + socket.getInetAddress());
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Main.puchToLog("Stoped listening for car connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendMessages();
    }

    public void sendMessages() {
        while (true) {
            if (out != null) {
                out.println(Main.message);
            }
        }
    }
}
