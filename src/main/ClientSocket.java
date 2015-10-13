package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ClientSocket implements Runnable {

    int portNumber = 1234;
    
    ArrayList<Socket> sockets;
    ArrayList<BufferedReader> ins;
    ArrayList<PrintWriter> outs;

    public ClientSocket() {
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                Main.puchToLog("Client connection established on IP:" + socket.getInetAddress());
                outs.add(new PrintWriter(socket.getOutputStream(), true));
                ins.add(new BufferedReader(new InputStreamReader(socket.getInputStream())));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void sendMessages(){
        while (true) {
            
        }
    }
}
