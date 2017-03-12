package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file SetPrefixCommand.java
 * @author Blue
 * @version 0.1
 * @brief Allow the user to change the prefix of the bot. Must be completed soon, still bugged
 */
public class SetPrefixCommand implements Command {

    private final String HELP = "The command `setprefix` let you change the prefix used by the bot.\n The default prefix is `!` \n\nUsage : `!setprefix yourPrefix`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help") || args.length > 1) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        MainBot.getPrefixes().put(event.getGuild(), args[0]);
        event.getTextChannel().sendMessage("The prefix has been updated to " + MainBot.getPrefixes().get(event.getGuild()));
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
