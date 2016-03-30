package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.BoxLayout;

public class Main extends Frame {

    //Message to send to Clients
    //Consists of 3 Chars
    public static String message;

    //All connected Sockets
    //Max one Car and as many clients as you want
    ClientConnect client;
    CarSocket car;

    //Files for the log
    public static TextArea logWindow;
    public static BufferedWriter logFile;

    public static Panel debugPanel;
    public static TextField currentMessage;
    public static TextField connectedSockets;

    public Main() {
        super("GA Server");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        createLog();

        message = "128 128 128";
        
        debugPanel = new Panel();
        debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.Y_AXIS));
        currentMessage = new TextField("Message: " + message);
        debugPanel.add(currentMessage);
        connectedSockets = new TextField("Sockets: ");
        debugPanel.add(connectedSockets);
        add(debugPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Main.pushToLog("Closing Server, prepare for the end!");
                try {
                    logFile.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(-1);
            }
        });

        setVisible(true);

        client = new ClientConnect();
        car = new CarSocket();
    }

    private static Calendar cal;
    private static SimpleDateFormat sdf;

    private void createLog() {
        
        cal = Calendar.getInstance();
        sdf = new SimpleDateFormat("yyyy-dd-dd HH:mm:ss");

        logWindow = new TextArea();
        logWindow.setEditable(false);
        add(logWindow, BorderLayout.NORTH);

        try {
            //Create a new logfile on the Desktop with the current date as the name 
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            String time = (sdf.format(cal.getTime()));
            File file = new File(System.getProperty("user.home")
                    + "\\Desktop\\" + time + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
            logFile = new BufferedWriter(fw);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        pushToLog("Server Started");
    }

    public static void pushToLog(String s) {
        if (logFile != null) {
            String time = sdf.format(cal.getTime());

            String output = time + " - " + s + "\n";

            logWindow.append(output);
            try {
                logFile.write(output);
                logFile.newLine();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Main();
    }
}
