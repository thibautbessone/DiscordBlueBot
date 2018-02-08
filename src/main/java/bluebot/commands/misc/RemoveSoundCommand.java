package bluebot.commands.misc;

import bluebot.utils.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.io.File;

/**
 * @file RemoveSoundCommand.java
 * @author Blue
 * @version 0.1
 * @brief Allow the user to delete uploaded sounds
 */
public class RemoveSoundCommand implements Command {

    private final String HELP = "The command `rmsound` allows you to delete a target from your server soundboard." +
            "\nThis command requires the manage messages permission." +
            "\n\nUsage : `!rmsound yourSound`";
    private boolean permissionFail = false;
    private File target;

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
        target = new File("soundboard" + "/" + event.getGuild().getId() + "/" + args[0] + ".mp3");
        String msg = new String();
        if(target.delete()){
            System.out.println("Sound " + target.getName() + " deleted");
            msg = "The sound `" + target.getName() + "` has been deleted.";
        } else {
            msg = "The sound `" + target.getName() + "` doesn't exist.";
        }
        event.getTextChannel().sendMessage(msg).queue();
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
