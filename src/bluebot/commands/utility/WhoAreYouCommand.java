package bluebot.commands.utility;

import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.io.*;

/**
 * @file WhoAreYouCommand.java
 * @author Blue
 * @version 0.2
 * @brief Provides info on the bot : creator, links, etc
 */
public class WhoAreYouCommand implements Command {

    private final String HELP = "The command `whoareyou` provides some information about the bot. \n\nUsage : `!whoareyou`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {
            event.getTextChannel().sendMessage(help());
            return;
        }
        else {
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
                event.getTextChannel().sendMessage("No information given. I'm very mysterious at the moment.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            event.getTextChannel().sendMessage(infoText);
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
