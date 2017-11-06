package bluebot.commands.owner;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @file SetGameCommand.java
 * @author Blue
 * @version 0.1
 * @brief Sets the current game of the bot
 */
public class SetGameCommand implements Command {

    private final String HELP = "The command `setgame` change the current bot activity to the one given in parameter." +
                                "\nThis command requires to be the owner of the bot." +
                                "\n\nUsage : `!setgame activity`";
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
        String text = new String();
        for(String arg : args) {
            text += arg + " ";
        }
        MainBot.getJda().getPresence().setGame(Game.of(text));
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
