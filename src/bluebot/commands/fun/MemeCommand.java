package bluebot.commands.fun;

import bluebot.utils.Command;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONArray;

import java.util.Random;

/**
 * @file SetPrefixCommand.java
 * @author Blue
 * @version 0.1
 * @brief Allow the user to change the prefix of the bot. Must be completed soon, still bugged
 */

public class MemeCommand implements Command {

    private final String HELP = "The command `meme` lets you post a meme with custom text.\n\nUsage : `!meme list to display etc`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
       //TODO
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
