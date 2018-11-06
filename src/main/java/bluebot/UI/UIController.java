package bluebot.UI;

import bluebot.MainBot;
import javafx.event.ActionEvent;
import net.dv8tion.jda.core.JDA;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * @file UIController.java
 * @author Blue
 * @version 0.1
 * @brief Controller of the JavaFX app
 */

public class UIController {

    private boolean running = false;

    public void startBot(ActionEvent actionEvent) {
        if (!running) {
            System.out.println("Bot starting ...");
            new MainBot();
            running = true;
        }
    }

    public void stopBot(ActionEvent actionEvent) {
        try {
            for(JDA shard : MainBot.getJdaList()) {
                shard.shutdown();
            }
            System.out.println("Bot stopped.");
            running = false;
        } catch (NullPointerException nullPtr) {
            System.out.println("No running instance of the bot.");
        }
    }

    public void openConfig(ActionEvent actionEvent) throws IOException {

        File configFile = new File("config.blue");
        if(configFile.exists() && !configFile.isDirectory()) {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                String cmd = "rundll32 url.dll,FileProtocolHandler config.blue";
                Runtime.getRuntime().exec(cmd);
            }
            else {
                Desktop.getDesktop().edit(new File("config.blue"));
            }
        }
        else {
            System.out.println("File config.blue not found.");
        }
    }

    public void quit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void openWebsite(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("http://bluebot.pw"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void invite(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URI("https://discord.gg/rSekkJv"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
