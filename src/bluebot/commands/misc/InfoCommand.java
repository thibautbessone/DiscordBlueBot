package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file InfoCommand.java
 * @author Blue
 * @version 0.1
 * @brief Provides information on the bot
 */
public class InfoCommand implements Command {

    private final String HELP = "The command `info` provides information on the bot.\n\nUsage : `!info`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {
            event.getTextChannel().sendMessage(help());
            return;
        }
        else {
            //TODO
            int usersNumber = MainBot.getJda().getUsers().size();
            int serversNumber = MainBot.getJda().getGuilds().size();
            int responseNumber = MainBot.getJda().getResponseTotal();
            String game = MainBot.getJda().getSelfInfo().getCurrentGame().getName();

            String message = new String();
            message += "Current users : `" + usersNumber + "`\n";
            message += "Current servers : `" + serversNumber + "`\n";
            message += "Responses number : `" + responseNumber + "`\n";
            message += "Current game : `" + game + "`\n";

             event.getTextChannel().sendMessage(message);

            /*
            -> Users number
            -> Servers number
            -> Uptime ?
            -> Version ?
            -> Response total number ? dunno


             */
        }

    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
