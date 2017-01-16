package bluebot.commands.utility;

import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file PingCommand.java
 * @author Blue
 * @version 0.2
 * @brief Classic ping command
 */
public class PingCommand implements Command {

    private final String HELP = "The command `ping` pings the bot (to check if it's online). \nUsage : `!ping`";

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
            event.getTextChannel().sendMessage("Pong ! Stop bothering me please, I'm being developed.");
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
