package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.Timer;

public class CarSocket {

    int portNumber = 25566;

    ServerSocket serverSocket;
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    Timer inTimer;
    Timer outTimer;
    Runnable search;

    public CarSocket() {
        search = new Runnable() {
            @Override
            public void run() {
                Main.pushToLog("Lisening for Car connection");
                try {
                    serverSocket = new ServerSocket(portNumber);
                    socket = serverSocket.accept();
                    Main.pushToLog("Car connection established on IP:" + socket.getInetAddress());
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    Main.pushToLog("Stopped listening for car connection");
                } catch (IOException e) {
                }
                outTimer.start();
                inTimer.start();
            }
        };
        new Thread(search).start();

        inTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((char) in.read() == 'd') {
                        socket.close();

                        outTimer.stop();
                        inTimer.stop();
                        new Thread(search).start();
                    }
                } catch (IOException ex) {
                }
            }
        });

        outTimer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (out != null) {
                    out.println(Main.message);
                }
            }
        });
    }
}
