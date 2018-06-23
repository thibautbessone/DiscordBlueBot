package bluebot.commands.utility;
 
import bluebot.utils.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
 
/**
 * @file mproomCommand.java
 * @author Ubksu615
 * @version 0.1
 * @brief Make the bot make a private music room for you!. Originally created to try giving parameters to bot commands
 */
public class mproom implements Command {
 
    private final String HELP = "The command `mproom` makes the bot open private music room. \n\nUsage : `!mproom 1word`";
 
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            return false;
        } else return true;
    }
 
    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String text = new String();
        for(String arg : args) {
            text += "https://togethertube.com/rooms/" + arg + "3647";
        }
        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(text).queue();
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
