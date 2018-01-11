package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.util.List;

/**
 * @file SetAutoRoleCommand.java
 * @author Blue
 * @version 0.3
 * @brief Allows the admin of the server to set a role automatically assigned when an user joins the server.
 */
public class SetAutoRoleCommand implements Command {

    private final String HELP = "The command `setautorole` let the owner automatically assign the specified role to new users." +
                                "\nThis command requires the administration permissions." +
                                "\n\nUsage : `!setautorole role`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getTextChannel(), event.getMember(), Permission.ADMINISTRATOR)) {
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
        String roleName = new String();
        for(String arg : args) {
            roleName += arg + " ";
        }
        roleName = roleName.substring(0, roleName.length() -1);
        List<Role> roleList = event.getGuild().getRoles();
        for(Role role : roleList) {
            if(roleName.equals(role.getName())) {
                MainBot.getAutoRoleList().put(event.getGuild().getId(), role.getName());
                event.getTextChannel().sendMessage("The role given on join is now " + MainBot.getAutoRoleList().get(event.getGuild().getId())).queue();
                return;
            }
        }
        event.getTextChannel().sendMessage("The specified role doesn't exist").queue();
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
