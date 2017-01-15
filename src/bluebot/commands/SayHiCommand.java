package bluebot.commands;

import bluebot.utils.Command;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * @file SayHiCommand.java
 * @author Blue
 * @version 0.3
 * @brief Says Hi ! to the mentioned users
 */
public class SayHiCommand implements Command {

    private final String HELP = "The command `sayhi` makes the bot say hi and mentioning the user given. \n\nUsage : `!say hi @User`";


    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        }
        else {
            if (event.getMessage().getMentionedUsers().isEmpty())
            {
                event.getTextChannel().sendMessage("No user mentioned.");
            }
            else {
                List<User> mentionedUsers = event.getMessage().getMentionedUsers();
                for(User u : mentionedUsers) {
                    event.getTextChannel().sendMessage("Hi " + u.getAsMention() + " !");
                }
                event.getMessage().deleteMessage();
            }
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
