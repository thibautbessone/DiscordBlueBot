package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file AddBadWordCommand.java
 * @author Blue
 * @version 0.2
 * @brief Adds a badwords to the forbidden words list
 */
public class AddBadWordCommand implements Command {

    private final String HELP = "The command `addbw` adds a word to the forbidden words list.\n\nUsage : `!addbw yourWord`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length == 0 || args[0].equals("help") || args.length > 1) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MainBot.getBadWords().add(args[0]);
        event.getTextChannel().sendMessage("The word has been added to the badwords list.");
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            event.getTextChannel().sendMessage(help());
        }

    }
}
