package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.entities.Role;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import java.util.List;

/**
 * @file SetAutoRoleCommand.java
 * @author Blue
 * @version 0.1
 * @brief Allows the admin of the server to set a role automatically assigned when an user joins the server.
 */
public class SetAutoRoleCommand implements Command {

    private final String HELP = "The command `setautorole` let the owner automatically assign the specified role to new users.\n\nUsage : `!setautorole role`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length != 1 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        } else {
            if(event.getAuthor() == event.getGuild().getOwner()) {
                List<Role> roleList = event.getGuild().getRoles();
                for(Role role : roleList) {
                    if(args[0].equals(role.getName())) {
                        MainBot.getAutoRoleList().put(event.getGuild().getId(), role.getName());
                        event.getTextChannel().sendMessage("The role given on join is now " + MainBot.getAutoRoleList().get(event.getGuild().getId()));
                        System.out.println("<<<<" + MainBot.getAutoRoleList().get(event.getGuild().getId()) + ">>>>");
                        return;
                    }
                }
                event.getTextChannel().sendMessage("The specified role doesn't exist");
            } else {
                event.getTextChannel().sendMessage("Only the owner of the server can use the `setautorole` command");
            }

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
