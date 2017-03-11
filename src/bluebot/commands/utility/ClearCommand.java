package bluebot.commands.utility;

import bluebot.utils.Command;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.exceptions.PermissionException;

/**
 * @file ClearCommand.java
 * @author Blue
 * @version 0.2
 * @brief The clear command is used to delete the number of messages given in parameter since the last message posted.
 */
public class ClearCommand implements Command {

    private final String HELP = "The command `clear` deletes the number of messages given in parameter. \n\nUsage : `!clear number (over 1)`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            int nbToDelete = Integer.parseInt(args[0]);
            if(nbToDelete < 1 || nbToDelete > 100) {
                event.getTextChannel().sendMessage("Invalid number. The number must be chosen between `1` and `100` (included)");
                return;
            }
            event.getTextChannel().deleteMessages(event.getTextChannel().getHistory().retrieve(nbToDelete + 1));
            event.getTextChannel().sendMessage("Done ! " + nbToDelete + " messages deleted.");
        } catch (NumberFormatException e) {
            event.getTextChannel().sendMessage("Invalid entry. Please type a number.");
        }
    }


    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    if (!success) {
        event.getTextChannel().sendMessage(help());
    }

    }
}
