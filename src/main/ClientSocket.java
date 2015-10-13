package main;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    public synchronized void getMessages(){
        while (true) {
            for (BufferedReader br : ins) {
                try {
                    //väntar på att elias bestämmer vad han ska skicka
                    Main.message = br.readLine();
                } catch (IOException ex) {
                }
            }
        }
    }
}
