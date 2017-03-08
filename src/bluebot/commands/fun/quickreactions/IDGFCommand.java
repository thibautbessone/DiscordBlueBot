package bluebot.commands.fun.quickreactions;

import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file NopeCommand.java
 * @author Blue
 * @version 0.1
 * @brief Posts https://www.youtube.com/watch?v=vsa1ZvzFgvU
 */
public class IDGFCommand implements Command {

    private final String HELP = "The command `idgf` posts a link to a fanfare. \n\nUsage : `!idgf`";

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
            event.getTextChannel().sendMessage("https://www.youtube.com/watch?v=vsa1ZvzFgvU");
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