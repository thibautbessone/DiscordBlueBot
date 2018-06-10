package bluebot.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

/**
 * @file LogSystem.java
 * @author Blue
 * @version 0.1
 * @brief Creates a log file (for easier bug reports)
 */
public class LogSystem {

    public static void run() {
        //PrintStream console = System.err; //save

        File file = new File("log.blue");
        FileOutputStream fileOS = null;
        try {
            fileOS = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream printS = new PrintStream(fileOS);

        System.setOut(printS); //for infos
        System.setErr(printS); //for errors

        System.out.println("###############################################################################");
        System.out.println("Don't forget to add this file to bug reports with your ID & your server ID !");
        System.out.println("###############################################################################\n");
    }
}
