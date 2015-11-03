package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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
        while (true) {
            try {
                String s = in.readLine();
                System.out.println(s);
                Main.message = s;
            } catch (IOException ex) {
            }
        }
    }
}
