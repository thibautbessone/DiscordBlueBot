package bluebot.commands.owner;

import bluebot.MainBot;
import bluebot.utils.Command;
import bluebot.utils.JSONSaver;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @file ShutDownCommand.java
 * @author Blue
 * @version 0.1
 * @brief Shutdowns the bot (emergency command)
 */
public class ShutDownCommand implements Command {

    private final String HELP = "The command `shutdown` stops the bot." +
            "\nThis command requires to be the owner of the bot." +
            "\n\nUsage : `!shutdown`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {
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
        System.out.println("Forced shutdown ...");
        new JSONSaver();
        event.getTextChannel().sendMessage("I've been shut down.").queue();
        for(JDA shard : MainBot.getJdaList()) {
            shard.shutdown();
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
