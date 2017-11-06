/*
package bluebot.commands.utility;

import bluebot.utils.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

*/
/**
 * @file ClearCommand.java
 * @author Blue
 * @version 0.2
 * @brief The clear command is used to delete the number of messages given in parameter since the last message posted.
 *//*

public class ClearCommand implements Command {

    private final String HELP = "The command `clear` deletes the number of messages given in parameter." +
                                "\nThis command requires the manage messages permission." +
                                " \n\nUsage : `!clear number (over 1)`";
    private boolean permissionFail = false;
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getGuild(), event.getMember(), Permission.MESSAGE_MANAGE)) {
                return true;
            } else {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you don't have the permission to do that.");
                permissionFail = true;
                return false;
            }
        }
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        try {
            int nbToDelete = Integer.parseInt(args[0]);
            if(nbToDelete < 1 || nbToDelete > 100) {
                event.getTextChannel().sendMessage("Invalid number. The number must be chosen between `1` and `100` (included)").queue();
                return;
            }
            event.getTextChannel().deleteMessages(event.getTextChannel().getHistory().retrieve(nbToDelete + 1)).queue();
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
*/
