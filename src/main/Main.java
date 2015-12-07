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
    public static final Object lock1 = new Object();

    //All connected Sockets
    //Max one Car and as many clients as you want
    ClientConnect client;
    CarSocket car;

    //Files for the log
    public static TextArea logWindow;
    public static BufferedWriter logFile;

    public static Panel debugPanel;
    public static TextField area1;
    public static TextField area2;
    public static TextField area3;
    public static TextField area4;

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super("GA Server");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        createLog();

        debugPanel = new Panel();
        debugPanel.setLayout(new BoxLayout(debugPanel, BoxLayout.Y_AXIS));
        area1 = new TextField("Message: ");
        debugPanel.add(area1);
        area2 = new TextField("Sockets: ");
        debugPanel.add(area2);
        area3 = new TextField("east");
        debugPanel.add(area3);
        area4 = new TextField("west");
        debugPanel.add(area4);
        add(debugPanel, BorderLayout.SOUTH);

        message = "128 128 128";

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    logFile.close();
                } catch (IOException ex) {
                    System.out.println("Couldn't Save LogFile");
                }
                System.exit(0);
            }
        });

        setVisible(true);

        client = new ClientConnect();
        car = new CarSocket();
    }

    private void createLog() {
        logWindow = new TextArea();
        logWindow.setEditable(false);
        add(logWindow, BorderLayout.NORTH);

        try {
            //Create a new logfile on the Desktop
            //Probobly only works for windows
            SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
            String time = (sdf.format(Calendar.getInstance().getTime()));
            File file = new File(System.getProperty("user.home")
                    + "\\Desktop\\logFiler\\" + time + ".txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            logFile = new BufferedWriter(fw);
        } catch (IOException ex) {
        }
    }

    public static void pushToLog(String s) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-dd HH:mm:ss");
        String time = (sdf.format(cal.getTime()));

        String output = time + " - " + s + "\n";

        logWindow.append(output);
        try {
            logFile.append(output);
            logFile.newLine();
        } catch (IOException ex) {
        }
    }
}
