package bluebot.commands.utility;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Map;

/**
 * @file HelpCommand.java
 * @author Blue
 * @version 0.1
 * @brief Displays general help, the list of all commands available and how to get help for a specific command.
 */


public class HelpCommand implements Command {

    private final String HELP = "The command `help` displays the current available commands for the bot. \nUsage : `!help`";
    String TEXT = "The currently available commands are listed below. All commands must be prefixed with a `!`. To obtain more information on a command, just type `!command help`\n\n";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
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
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
