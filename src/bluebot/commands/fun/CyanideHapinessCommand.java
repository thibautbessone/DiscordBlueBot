package bluebot.commands.fun;

import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Random;

/**
 * @file CyanideHapinessCommand.java
 * @author Blue
 * @version 0.1
 * @brief Posts a Cyanide & Hapiness comic
 */
public class CyanideHapinessCommand implements Command {

    private final String HELP = "The command `c&h` posts a Cyanide & Hapiness comic from explosm.net. \n\nUsage : `!c&h latest` - posts the latest comic, `!c&h random` - posts a random comic";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        } else {
            if(args[0].equals("latest")) {
                event.getTextChannel().sendMessage("http://explosm.net/comics/latest");
            } else if(args[0].equals("random")) {
                Random r = new Random();
                int result = r.nextInt(3500) + 1000;
                event.getTextChannel().sendMessage("http://explosm.net/comics/" + result);
            }
            else {
                event.getTextChannel().sendMessage(help());
            }
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
