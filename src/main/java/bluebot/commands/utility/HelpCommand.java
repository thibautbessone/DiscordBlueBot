package bluebot.commands.utility;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Map;

/**
 * @file HelpCommand.java
 * @author Blue
 * @version 0.2
 * @brief Displays general help, the list of all commands available and how to get help for a specific command.
 */


public class HelpCommand implements Command {

    private final String HELP = "The command `help` displays the current available commands for the bot. \nUsage : `!help`";
    private String TEXT = "The currently available commands are listed below. All commands must be prefixed with a `!` (by default). \nTo obtain more information on a command, just type `!command help`\n";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        String funCommandsList = "**Fun commands : ** ";
        for(Map.Entry<String, Command> entry : MainBot.funCommands.entrySet()) {
            String command = entry.getKey();
            funCommandsList += "`" + command + "`" + "\t";
        }

        String modUtilCommandsList = "\n\n**Moderation & Utlity commands : ** ";
        for(Map.Entry<String, Command> entry : MainBot.modUtilCommands.entrySet()) {
            String command = entry.getKey();
            modUtilCommandsList += "`" + command + "`" + "\t";
        }

        String miscCommandsList = "\n\n**Miscellaneous commands : ** ";
        for(Map.Entry<String, Command> entry : MainBot.miscCommands.entrySet()) {
            String command = entry.getKey();
            miscCommandsList += "`" + command + "`" + "\t";
        }

        String ownerCommandsList = "\n\n**Owner-only commands : ** ";
        for(Map.Entry<String, Command> entry : MainBot.ownerCommands.entrySet()) {
            String command = entry.getKey();
            ownerCommandsList += "`" + command + "`" + "\t";
        }

        String footer = "\n\nIf you need more help, join the support server : https://discord.gg/rSekkJv \n\nThanks for using **BlueBot** ! ";

        String finalList = funCommandsList + modUtilCommandsList + miscCommandsList + ownerCommandsList + footer;
        event.getAuthor().openPrivateChannel().queue( (channel) -> channel.sendMessage(TEXT + "\n" + finalList + "").queue() );
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            event.getTextChannel().sendMessage(help()).queue();
        }
    }
}
