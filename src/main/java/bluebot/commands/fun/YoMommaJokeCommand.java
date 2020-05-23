package bluebot.commands.fun;

import bluebot.utils.Command;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONObject;

import java.io.IOException;
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
            String joke;
            OkHttpClient caller = new OkHttpClient();
            Request request = new Request.Builder().url("http://api.yomomma.info/").build();
            List<User> mentionedUsers = event.getMessage().getMentionedUsers();
            for(User u : mentionedUsers) {
                try {
                    Response response = caller.newCall(request).execute();
                    JSONObject json = new JSONObject(response.body().string());
                    joke = (String) json.get("joke");
                    event.getTextChannel().sendMessage(u.getAsMention() + " " + joke).queue();
                } catch (IOException | NullPointerException e) {
                    event.getTextChannel().sendMessage("No joke found").queue();
                }
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
            event.getTextChannel().sendMessage(help()).queue();
        }
    }
}
