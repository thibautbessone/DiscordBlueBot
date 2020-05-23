package bluebot.commands.fun;

import bluebot.utils.Command;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @file CatCommand.java
 * @author Blue
 * @version 0.2
 * @brief Posts a random cat image/gif from random.cat
 */
public class CatCommand implements Command {

    private final String HELP = "The command `cat` posts a cat image from random.cat.\n\nUsage : `!cat`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String url;
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("https://aws.random.cat/meow").build();
        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            url = (String) json.get("file");
            event.getTextChannel().sendMessage(url).queue();
        } catch (IOException | NullPointerException | JSONException e) {
            event.getTextChannel().sendMessage("The random.cat API might be down at the moment").queue();
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
