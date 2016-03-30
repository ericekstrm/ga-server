package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class CarSocket {

    int portNumber = 25566;

    ServerSocket serverSocket;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    Runnable search;
    Runnable outThread;
    Runnable inThread;

    public CarSocket() {
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException ex) {
        }
        search = new Runnable() {
            @Override
            public void run() {
                Main.pushToLog("Lisening for Car connection");
                try {
                    socket = serverSocket.accept();
                    Main.pushToLog("Car connection established on IP:" + socket.getInetAddress());
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Main.pushToLog("Stopped listening for car connection");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                new Thread(outThread).start();
                new Thread(inThread).start();
            }
        };
        new Thread(search).start();

        outThread = new Runnable() {
            @Override
            public void run() {
                while (!socket.isClosed()) {
                    if (out != null) {
                        out.println(Main.message);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
            }
        };

        inThread = new Runnable() {
            @Override
            public void run() {
                while (!socket.isClosed()) {
                    try {
                        if ((char) in.read() == 'd') {
                            socket.close();
                            Main.pushToLog("Car Connection Lost");
                            new Thread(search).start();
                        }
                    } catch (IOException ex) {
                    }
                }
            }
        };
    }
}
