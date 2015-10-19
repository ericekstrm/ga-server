package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JTextArea;

public class Main extends Frame {

    //Message to send to Clients
    //Consists of 3 Chars
    public static String message;

    //All connected Sockets
    //Max one Car and as many clients as you want
    ClientSocket client;
    CarSocket car;

    //Files for the log
    public static final String newLine = "\n";
    public static JTextArea logWindow;
    public static PrintWriter logFile;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super("GA Server");
        setSize(400, 400);
        setLocationRelativeTo(null);

        createLog();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);

        client = new ClientSocket();
        car = new CarSocket();
    }

    private void createLog() {
        logWindow = new JTextArea();
        logWindow.setEditable(false);
        add(logWindow);

        try {
            //Create a new logfile on the Desktop
            //Probobly only works for windows
            logFile = new PrintWriter(System.getProperty("user.home") + "\\Desktop\\logfile.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println("error creating logfile");
        }
    }

    public static void puchToLog(String s) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = (sdf.format(cal.getTime()));

        String output = time + " - " + s + newLine;

        logWindow.append(output);
        logFile.println(output);
    }
}
