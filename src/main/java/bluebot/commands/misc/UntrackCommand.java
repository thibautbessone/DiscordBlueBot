package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

/**
 * @file UntrackCommand.java
 * @author Blue
 * @version 0.1
 * @brief Makes the bot stop tracking a user.
 */
public class UntrackCommand implements Command {

    private final String HELP = "The command `untrack` makes the bot stop posting Twitch notifications about the specified user." +
            "\nThis command requires the manage messages permission." +
            " \n\nUsage : `!untrack @TrackedUser`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help") || args.length > 1) {return false;}
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
            if(MainBot.getStreamerList().containsKey(event.getMessage().getMentionedUsers().get(0).getId())) {
                MainBot.getStreamerList().remove(event.getMessage().getMentionedUsers().get(0).getId());
            } else {
                event.getTextChannel().sendMessage("This user is not tracked.").queue();
                return;
            }
            event.getTextChannel().sendMessage("This user is not tracked anymore.").queue();
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
