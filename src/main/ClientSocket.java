package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientSocket implements Runnable {

    Socket socket;
    BufferedReader in;
    PrintWriter out;
    ArrayList<ClientSocket> sockets;

    public ClientSocket(Socket socket, ArrayList<ClientSocket> sockets ) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.sockets = sockets;
        Thread th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {
        String s = "";
        while (!s.equals("d")) {
            try {
                s = in.readLine();
                System.out.println(s);
                
                //Validera inkommande medelande
                //förutsätter att Elias skickar nåt som är halvvättigt
                String[] subs = s.split(" ");
                for (String sub : subs) {
                    while (sub.length() < 3) {
                        sub = "0" + sub;
                    }
                }
                s = subs[0] + " " + subs[1] + " " + subs[2];
                
                Main.message = s;
            } catch (IOException ex) {
            }
        }
        try {
            socket.close();
            sockets.remove(this);
        } catch (IOException ex) {
            Logger.getLogger(ClientSocket.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
