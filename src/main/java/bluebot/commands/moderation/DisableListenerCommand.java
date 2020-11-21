package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @file DisableListenerCommand.java
 * @author Blue
 * @version 0.1
 * @brief Disable functionalities of BlueBot on a server.
 */
public class DisableListenerCommand implements Command {

    private static final Map<String, ArrayList<String>> link;
    static
    {
        link = new HashMap<>();
        link.put("twitch", MainBot.getTwitchDisabled());
        link.put("bw", MainBot.getBwDisabled());
        link.put("cleverbot", MainBot.getCleverBotDisabled());
        link.put("userevent", MainBot.getUserEventDisabled());
        link.put("serversb", MainBot.getServerSBDisabled());
    }

    private final String HELP = "The command `disable` disable the specified BlueBot functionality." +
            "\nThis command requires the manage messages permission." +
            "\n\nUsage :" +
            "\n\n`!disable twitch` : disable Twitch notifications" +
            "\n`!disable bw` : disable the bad words filter" +
            "\n`!disable cleverbot` : disable CleverBot integration" +
            "\n`!disable userevent` : disable role auto-assign and welcome/goodbye messages" +
            "\n`!disable serversb` : disable the dedicated soundboard for the general one";

    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getTextChannel(), event.getMessage().getMember(), Permission.MESSAGE_MANAGE)) {
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
        if (link.containsKey(args[0])) {
            if(!link.get(args[0]).contains(event.getGuild().getId())) {
                link.get(args[0]).add(event.getGuild().getId());
                event.getTextChannel().sendMessage("The functionality has been disabled.").queue();
            } else {
                event.getTextChannel().sendMessage("This functionality is already disabled.").queue();
            }
        } else {
            event.getTextChannel().sendMessage("Invalid entry, please try again.").queue();
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
