package bluebot.commands.fun.quickreactions;

import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file WatCommand.java
 * @author Blue
 * @version 0.1
 * @brief Posts the WAT grandma image.
 */
public class WatCommand implements Command {
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
            event.getTextChannel().sendMessage("http://i.imgur.com/7kZ562z.jpg");
        }
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
