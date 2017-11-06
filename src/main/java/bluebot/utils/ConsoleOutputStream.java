package bluebot.utils;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @file ConsoleOutputStream.java
 * @author Blue
 * @version 0.1
 * @brief Sets stdout to the display console of the interface.
 */
public class ConsoleOutputStream extends OutputStream {
    private JTextArea displayZone;

    public ConsoleOutputStream(JTextArea displayZone) {
        this.displayZone = displayZone;
    }

    @Override
    public void write(int b) throws IOException {
        //This is used to redirect the data into to displayZone
        displayZone.append(String.valueOf((char)b));
        displayZone.setCaretPosition(displayZone.getDocument().getLength());
    }
}
