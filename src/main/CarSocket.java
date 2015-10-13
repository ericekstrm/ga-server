package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CarSocket implements Runnable {
    
    int portNumber = 1234;
    
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public CarSocket() {
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                socket = serverSocket.accept();
                Main.puchToLog("Car connection established on IP:" + socket.getInetAddress());
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void getMessages(){
        while (true) {
            
        }
    }
}
