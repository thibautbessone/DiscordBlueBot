package bluebot;

import bluebot.utils.ConsoleOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;

/**
 * @file BlueBotInterface.java
 * @author Blue
 * @version 0.2
 * @brief The visual interface of the bot, to start/stop it.
 * I know this is absolutely spaghetti/horrible/disgusting code, but my point wasn't developing a real well-made interface at tis moment.
 * The interface code will be cleaned later.
 */

public class BlueBotInterface extends JFrame {

    private Color bgColor = new Color(0, 136, 255);
    private Color textColor = Color.LIGHT_GRAY;
    private Color hoverColor = new Color(48, 48, 48);
    private Color interfaceBackgroundColor = Color.DARK_GRAY;
    public BlueBotInterface() {
        super("BlueBot");

        ImageIcon icon = new ImageIcon("icon.png");
        setIconImage(icon.getImage());

        JTextField title = new JTextField("BlueBot - a simple Discord bot");
        title.setBorder(null);
        title.setFont(new Font("Arial", Font.PLAIN, 25));
        title.setForeground(Color.LIGHT_GRAY);
        title.setBackground(bgColor);
        title.setEditable(false);
        title.setHorizontalAlignment(JTextField.CENTER);
        getContentPane().add(title,  BorderLayout.NORTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        JTextArea text = new JTextArea(20, 15);

        String infoText = new String();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("infoFile.blue")))) {
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    infoText += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            text.setText("No information given. I'm very mysterious at the moment.");
        } catch (IOException e) {
            e.printStackTrace();
        }


        text.setText(infoText);
        PrintStream console = new PrintStream(new ConsoleOutputStream(text));

        //keeps reference of standard output stream
        PrintStream standardOutBackup = System.out;

        //assigns stdout and stdrr to the console output
        System.setOut(console);
        //System.setErr(console);

        text.setBorder(null);
        text.setEditable(false);
        text.setAutoscrolls(true);
        text.setForeground(textColor);
        text.setBackground(interfaceBackgroundColor);
        JScrollPane output = new JScrollPane(text);
        output.getVerticalScrollBar().setBorder(null);

        mainPanel.add(output, BorderLayout.CENTER);
        getContentPane().add(mainPanel);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(bgColor);
        buttonPanel.setLayout(new FlowLayout());

        JButton start = new JButton("Start BlueBot");
        start.setBorder(null);
        start.setForeground(Color.LIGHT_GRAY);
        start.setBackground(interfaceBackgroundColor);
        start.setFocusPainted(false);
        start.setPreferredSize(new Dimension(250,  30));
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                text.setText(null);
                new MainBot();
                start.setEnabled(false);
                start.setText("BlueBot already running");
            }
        });

        start.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                start.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                start.setBackground(interfaceBackgroundColor);
            }
        });

        JButton stop = new JButton("Stop & Exit");
        stop.setBorder(null);
        stop.setForeground(textColor);
        stop.setBackground(interfaceBackgroundColor);
        stop.setFocusPainted(false);
        stop.setPreferredSize(new Dimension(250,  30));
        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    MainBot.getJda().shutdown();
                    System.out.println("Exiting ...");
                    System.exit(0);
                } catch (NullPointerException nullPtr) {
                    System.out.println("No instance of bot running.");
                }
            }
        });

        stop.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                stop.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                stop.setBackground(interfaceBackgroundColor);
            }
        });

        buttonPanel.add(start);
        buttonPanel.add(stop);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);


        //setPreferredSize(new Dimension(1000, 500));
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        BlueBotInterface blueBotConsole = new BlueBotInterface();
        blueBotConsole.setVisible(true);
        blueBotConsole.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

}
