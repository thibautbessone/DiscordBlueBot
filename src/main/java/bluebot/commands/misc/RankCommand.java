package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * @file RankCommand.java
 * @author Blue
 * @version 0.1
 * @brief Allows a user to get the specified rank using a specific keyword. //TEST DEV
 */
public class RankCommand implements Command {

    private final String HELP = "The command `rank` allows you to get the specified rank.\n\nUsage : `!rank desiredRank`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (args[0].equals("list")) {
            System.out.println(MainBot.getSelfAssignedRolesList().toString());
            System.out.println(MainBot.getBadWords().toString());
            String list = "**Available ranks : **\n\n";
            try {
                for (String rank : MainBot.getSelfAssignedRolesList().get(event.getGuild().getId())) {
                    list += rank + "\n";
                }
            } catch (NullPointerException e) {
                list = "No ranks available.";
            }
            event.getTextChannel().sendMessage(list).queue();
        } else {
            String roleName = "";
            for(String arg : args) {
                roleName += arg + " ";
            }
            roleName = roleName.substring(0, roleName.length() -1);
            if (args[0].equals("add")) {
                //TODO
                if (MainBot.getSelfAssignedRolesList().containsKey(event.getGuild().getId())) {
                    //Check if the role exists
                    List<Role> roleList = event.getGuild().getRoles();
                    for(Role role : roleList) {
                        if(roleName.equals(role.getName())) {
                            MainBot.getSelfAssignedRolesList().get(event.getGuild().getId()).add(roleName);
                            event.getTextChannel().sendMessage("The rank has been added to the available ranks.").queue();
                            return;
                        }
                    }
                    event.getTextChannel().sendMessage("The specified role doesn't exist").queue();
                }
            } else if (args[0].equals("rm")) {
                if (MainBot.getSelfAssignedRolesList().get(event.getGuild().getId()).contains(args[1])) {
                    for (int i = 0; i < MainBot.getSelfAssignedRolesList().get(event.getGuild().getId()).size(); ++i) {
                        if (args[1].equals(MainBot.getSelfAssignedRolesList().get(event.getGuild().getId()).get(i))) {
                            MainBot.getSelfAssignedRolesList().get(event.getGuild().getId()).remove(i);
                            event.getTextChannel().sendMessage("The rank `" + args[1] + "`has been removed from the rank list").queue();
                            return;
                        }
                    }
                } else {
                    event.getTextChannel().sendMessage("The specified rank doesn't exist.").queue();
                }
            }
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
