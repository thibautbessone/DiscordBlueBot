package bluebot.utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @file SaveThread.java
 * @author Blue
 * @version 0.1
 * @brief Thread used to periodically save the state of BlueBot.
 */
public class SaveThread extends Thread {
    public void run() {
        Timer timer = new Timer ();
        TimerTask autoSaveTask = new TimerTask () {
            @Override
            public void run () {
                JSONSaver saver = new JSONSaver();
                System.runFinalization();
                System.gc();
            }
        };
        timer.schedule(autoSaveTask, 100, 1000*60*60);
    }
}
