package bluebot.commands;

import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file SayCommand.java
 * @author Blue
 * @version 0.1
 * @brief Make the bot say the string given in parameter. Originally created to try giving parameters to bot commands
 */
public class SayCommand implements Command {

    private final String HELP = "The command `say` makes the bot say what you want. \n\nUsage : `!say your message`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        } else {
            String text = new String();
            for(String arg : args) {
                text += arg + " ";
            }
            event.getTextChannel().sendMessage(text);
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
