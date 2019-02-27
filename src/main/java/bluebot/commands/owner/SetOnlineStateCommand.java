package bluebot.commands.owner;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @file SetOnlineStateCommand.java
 * @author Blue
 * @version 0.1
 * @brief Sets the online status of the bot
 */
public class SetOnlineStateCommand implements Command {

    private final String HELP = "The command `setos` change the current online status of the bot." +
            "\nThis command requires to be the owner of the bot." +
            "\n\nUsage : `!setos online | idle | dnd (do not disturb) | invisible`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            return false;
        } else {
            if(event.getAuthor().getId().equals(MainBot.getBotOwner())) {
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
        try {
            if(args[0].equals("dnd")) {
                for(JDA shard : MainBot.getJdaList()) {
                    shard.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
                }
            } else {
                for(JDA shard : MainBot.getJdaList()) {
                    shard.getPresence().setStatus(OnlineStatus.valueOf(args[0].toUpperCase()));
                }
            }
            event.getTextChannel().sendMessage("Online status updated to " + args[0]).queue();
        } catch (IllegalArgumentException e) {
            event.getTextChannel().sendMessage("Incorrect status.").queue();
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
