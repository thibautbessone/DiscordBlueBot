package bluebot.commands;

import bluebot.Command;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Created by Thibaut on 11/01/2017.
 */
public class SayHiCommand implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMessage().getMentionedUsers().isEmpty())
        {
            event.getTextChannel().sendMessage("No user mentioned");
        }
        else {
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            for(User u : mentionedUsers) {
                event.getTextChannel().sendMessage("Hi " + u.getAsMention() + " !");
            }
        }
    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
    return;
    }
}
