package bluebot.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * @file LogSystem.java
 * @author Blue
 * @version 0.1
 * @brief Creates a log file (for easier bug reports)
 */
public class LogSystem {

    public static void run() {
        //PrintStream console = System.err; //save
        //Logs dates
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH-mm-ss");

        File file = new File("log" + LocalDate.now() + "Time" + sdf.format(cal.getTime()) + ".blue");
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
