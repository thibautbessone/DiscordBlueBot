package bluebot.commands.fun;

import bluebot.utils.Command;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * @file CatCommand.java
 * @author Blue
 * @version 0.1
 * @brief Posts a random cat image/gif from random.cat
 */
public class CatCommand implements Command {

    private final String HELP = "The command `cat` posts a cat image from random.cat.\n\nUsage : `!cat`";


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
            String url = new String();
            try {
                url = (String) Unirest.get("http://random.cat/meow").asJson().getBody().getObject().get("file");
                System.out.println();
            } catch (UnirestException ex) {
                event.getTextChannel().sendMessage("The random.cat API might be down");
            }
            event.getTextChannel().sendMessage(url);
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
