package bluebot.commands;

import bluebot.Command;
import bluebot.MainBot;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by Thibaut on 11/01/2017.
 */
public class PingCommand implements Command {

    private final String HELP = "Usage : !ping";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage("Pong !");
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
