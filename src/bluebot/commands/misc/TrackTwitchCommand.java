package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import bluebot.utils.listeners.TwitchListener;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.utils.PermissionUtil;

import java.util.List;

/**
 * @file TrackTwitchCommand.java
 * @author Blue
 * @version 0.2
 * @brief Makes the bot "track" a user in order to post a message when the user starts streaming.
 */
public class TrackTwitchCommand implements Command {

    private final String HELP = "The command `tracktwitch` makes the bot post a message when the specified user is streaming." +
                                "\nThis command requires the manage messages permission." +
                                " \n\nUsage : `!tracktwitch @TrackedUser streamLink`";
    private boolean permissionFail = false;
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args.length > 2 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getGuild(), event.getAuthor(), Permission.MESSAGE_MANAGE)) {
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
        if (event.getMessage().getMentionedUsers().isEmpty() || event.getMessage().getMentionedUsers().size() > 1) {
            event.getTextChannel().sendMessage("No user or too many users mentioned.");
        }
        else {
            try {
                MainBot.getStreamerList().put(event.getMessage().getMentionedUsers().get(0).getId(), args[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                event.getTextChannel().sendMessage("Stream is link missing.");
                return;
            }

            event.getTextChannel().sendMessage(event.getMessage().getMentionedUsers().get(0).getUsername() + " added to the tracked streamers");
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            if(!permissionFail) {
                event.getTextChannel().sendMessage(help());
            }
            permissionFail = false;
        }
    }
}
