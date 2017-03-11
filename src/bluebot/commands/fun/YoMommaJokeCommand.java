package bluebot.commands.fun;

import bluebot.utils.Command;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONObject;
import sun.plugin2.message.Message;

import java.util.List;


/**
 * @file YoMommaJokeCommand.java
 * @author Blue
 * @version 0.2
 * @brief Posts a random 'yo momma' joke from yomomma.info
 */

public class YoMommaJokeCommand implements Command {

    private final String HELP = "The command `ymjoke` mention the given user and post a 'yo momma' joke. \n\nUsage : `!ymjoke @User`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMessage().getMentionedUsers().isEmpty())
        {
            event.getTextChannel().sendMessage("No user mentioned.");
        }
        else {
            String joke = new String();
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            for(User u : mentionedUsers) {
                try {
                    joke = (String) Unirest.get("http://api.yomomma.info/").asJson().getBody().getObject().get("joke");
                } catch (UnirestException ex) {
                    event.getTextChannel().sendMessage("No joke found");
                }
                event.getTextChannel().sendMessage(u.getAsMention() + " " + joke);
            }
        }
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
