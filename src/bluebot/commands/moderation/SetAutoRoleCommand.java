package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.utils.PermissionUtil;

import java.util.List;

/**
 * @file SetAutoRoleCommand.java
 * @author Blue
 * @version 0.2
 * @brief Allows the admin of the server to set a role automatically assigned when an user joins the server.
 */
public class SetAutoRoleCommand implements Command {

    private final String HELP = "The command `setautorole` let the owner automatically assign the specified role to new users." +
                                "\nThis command requires the administration permissions." +
                                "\n\nUsage : `!setautorole role`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 1 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getGuild(), event.getAuthor(), Permission.ADMINISTRATOR)) {
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
        List<Role> roleList = event.getGuild().getRoles();
        for(Role role : roleList) {
            if(args[0].equals(role.getName())) {
                MainBot.getAutoRoleList().put(event.getGuild().getId(), role.getName());
                event.getTextChannel().sendMessage("The role given on join is now " + MainBot.getAutoRoleList().get(event.getGuild().getId()));
                return;
            }
        }
        event.getTextChannel().sendMessage("The specified role doesn't exist");
    }


    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if (!success) {
            if(!permissionFail) {
                event.getTextChannel().sendMessage(help());
            }
            permissionFail = false;
        }
    }
}
