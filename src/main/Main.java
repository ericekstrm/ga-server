package main;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JTextArea;

public class Main extends Frame {

    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        super("GA Server");
        setSize(400, 400);

        createMenuBar();
        createLog();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setVisible(true);

        createSocket();
    }

    MenuBar menuBar;
    Menu menu;
    MenuItem menuItem;

    private void createMenuBar() {

        //Create the menu bar.
        menuBar = new MenuBar();

        //Build the first menu.
        menu = new Menu("A Menu");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new MenuItem("A text-only menu item");
        menu.add(menuItem);

        menuItem = new MenuItem("Both text and icon");
        menu.add(menuItem);

        menuItem = new MenuItem("HEJ");
        menu.add(menuItem);

        menu.addSeparator();

        menuItem = new MenuItem("Exit");
        Frame f = this;
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
            }
        });
        menu.add(menuItem);

        setMenuBar(menuBar);
    }

    public static final String newLine = "\n";
    public JTextArea logWindow;
    PrintWriter logFile;

    private void createLog() {
        logWindow = new JTextArea();
        logWindow.setEditable(false);
        add(logWindow);

        try {
            logFile = new PrintWriter("C:\\Users\\Eric\\Desktop\\logfile.txt", "UTF-8");
        } catch (FileNotFoundException ex) {
            System.out.println("error creating logfile");
        } catch (UnsupportedEncodingException ex) {
            System.out.println("error creating logfile");
        }
    }

    private void puchToLog(String s) {

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = (sdf.format(cal.getTime()));

        String output = time + " - " + s + newLine;

        logWindow.append(output);
        logFile.println(output);
    }

    Socket clientSocket;
    PrintWriter out;
    BufferedReader in;
    int portNumber = 8080;

    private void createSocket() {
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
            puchToLog("Connection established on IP:" + clientSocket.getInetAddress());
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
