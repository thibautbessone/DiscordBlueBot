package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.commands.utility.JoinLeaveMessageContainer;
import bluebot.utils.Command;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.internal.utils.PermissionUtil;


/**
 * @file UserEventsMessagesCommand.java
 * @author Blue
 * @version 0.1
 * @brief Used to stock the messages posted by the bot on user events (join/leave)
 */
public class UserEventsMessagesCommand implements Command {

    private final String HELP = "The command `uemsg` (for User Events Messages) lets you change the welcome / goodbye messages for your server." +
                                "\n\nTo set the join / leave message, use the `msgJoin` / `msgLeave` argument right after the command. " +
                                "\nThe bot can replace the following words : `{USER}` and `{SERVER}` respectively by the user's name and the server's name." +
                                "\nYou can also decide whether to mention the user or not with the `-joinMention` and `-leaveMention` arguments." +
                                "\n\nExample : `!uemsg msgJoin Welcome to {USER} here on the **{SERVER}** server ! Enjoy your stay ! -joinMention` (you can use the regular Markdown styling of discord for italic, etc)";

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
        String confirmText;
        JoinLeaveMessageContainer container;
        if(MainBot.getUserEventsMessages().get(event.getGuild().getId()) != null) container = MainBot.getUserEventsMessages().get(event.getGuild().getId());
        else container = new JoinLeaveMessageContainer();
        //Raw text without options
        String text = "";
        for(String arg : args) {
            if(arg.equals("-joinMention")) {
                container.setJoinMention(true);
                continue;
            } else if(arg.equals("-leaveMention")) {
                container.setLeaveMention(true);
                continue;
            } else {
                container.setJoinMention(false);
                container.setLeaveMention(false);
            }
            text += arg + " ";
        }

        if(args[0].equals("msgJoin")) {
            text = text.replaceFirst("msgJoin", "");
            container.setJoinMessage(text);
            confirmText = "Welcome message updated.";
        } else if(args[0].equals("msgLeave")) {
            text = text.replaceFirst("msgLeave", "");
            container.setLeaveMessage(text);
            confirmText = "Leave message updated.";
        } else {
            event.getTextChannel().sendMessage("Missing `msgJoin` or `msgLeave` argument.").queue();
            return;
        }

        //Updates the Map entry
        MainBot.getUserEventsMessages().put(event.getGuild().getId(), container);
        event.getTextChannel().sendMessage(confirmText).queue();
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
