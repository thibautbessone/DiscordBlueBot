package bluebot.commands.utility;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

/**
 * @file HelpCommand.java
 * @author Blue
 * @version 0.2
 * @brief Displays general help, the list of all commands available and how to get help for a specific command.
 */


public class HelpCommand implements Command {

    private final String HELP = "The command `help` displays the current available commands for the bot. \nUsage : `!help`";
    private String TEXT = "The currently available commands are listed below. All commands must be prefixed with a `!` (by default). To obtain more information on a command, just type `!command help`\n\n";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String list = new String();
        for(Map.Entry<String, Command> entry : MainBot.commands.entrySet()) {
            String command = entry.getKey();
            list += command + "\n";
        }
        event.getTextChannel().sendMessage(TEXT + "```\n" + list + "```");
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
