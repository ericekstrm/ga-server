package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import static main.ClientConnect.*;
import static main.Main.*;

public class ClientSocket implements Runnable {

    Socket socket;
    BufferedReader in;
    PrintWriter out;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        String s = "";
        while (!socket.isClosed()) {
            try {
                s = in.readLine();
                if (s.equals("d")) {
                    socket.close();
                    sockets.remove(this);
                    area2.setText("Sockets: " + sockets.size());
                    Main.pushToLog("Client on IP: " + socket.getInetAddress() + " Disconnected");
                } else {
                    //Validera inkommande medelande
                    String[] subs = s.split(" ");
                    for (String sub : subs) {
                        while (sub.length() < 3) {
                            sub = "0" + sub;
                        }
                    }
                    s = subs[0] + " " + subs[1] + " " + subs[2];

                    Main.message = s;
                    area1.setText("Message: " + message);
                }
            } catch (IOException ex) {
            }
        }
    }
}
