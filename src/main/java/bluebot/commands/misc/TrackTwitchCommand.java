package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * @file TrackTwitchCommand.java
 * @author Blue
 * @version 0.3
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
        if (event.getMessage().getMentionedUsers().isEmpty() || event.getMessage().getMentionedUsers().size() > 1) {
            event.getTextChannel().sendMessage("No user or too many users mentioned.").queue();
        }
        else {
            try {
                MainBot.getStreamerList().put(event.getMessage().getMentionedUsers().get(0).getId(), args[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                event.getTextChannel().sendMessage("Stream is link missing.").queue();
                return;
            }

            event.getTextChannel().sendMessage(event.getMessage().getMentionedUsers().get(0).getName() + " added to the tracked streamers.").queue();
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
                event.getTextChannel().sendMessage(help()).queue();
            }
            permissionFail = false;
        }
    }
}
