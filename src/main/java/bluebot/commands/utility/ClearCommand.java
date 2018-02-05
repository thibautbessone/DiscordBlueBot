
package bluebot.commands.utility;

import bluebot.utils.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @file ClearCommand.java
 * @author Blue
 * @version 0.3
 * @brief The clear command is used to delete the number of messages given in parameter since the last message posted.
 */

public class ClearCommand implements Command {

    private final String HELP = "The command `clear` deletes the number of messages given in parameter." +
                                "\nThis command requires the manage messages permission." +
                                " \n\nUsage : `!clear number (1 to 99)`";
    private boolean permissionFail = false;
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getTextChannel(), event.getMember(), Permission.MESSAGE_MANAGE)) {
                return true;
            } else {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you don't have the permission to do that.").queue();
                permissionFail = true;
                return false;
            }
        }
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            int nbToDelete = Integer.parseInt(args[0]);
            if(nbToDelete < 1 || nbToDelete > 99) {
                event.getTextChannel().sendMessage("Invalid number. The number must be chosen between `1` and `99` (included)").queue();
                return;
            }
            List<Message> history = event.getTextChannel().getHistory().retrievePast(nbToDelete +1).complete();
            List<Message> msgToDelete = new ArrayList<>();
            for (Message msg : history) {
                if(DAYS.between(msg.getCreationTime().toLocalDateTime(), LocalDateTime.now()) < 14) {
                    msgToDelete.add(msg);
                }
            }
            event.getTextChannel().deleteMessages(msgToDelete).queue();
            event.getTextChannel().sendMessage("Done ! " + nbToDelete + " messages deleted.").queue();
        } catch (NumberFormatException e) {
            event.getTextChannel().sendMessage("Invalid entry. Please type a number.").queue();
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if (!success) {
            if(!permissionFail) {
                event.getTextChannel().sendMessage(help()).queue();
            }
            permissionFail = false;
        }
    }
}

