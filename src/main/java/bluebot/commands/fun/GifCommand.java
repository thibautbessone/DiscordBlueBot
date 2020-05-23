package bluebot.commands.fun;

import bluebot.utils.Command;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

/**
 * @file GifCommand.java
 * @author Blue
 * @version 0.2
 * @brief Makes the bot post a gif searched on Giphy with the specified keywords
*/

public class GifCommand implements Command {

    private final String HELP = "The command `gif` posts the first search result from giphy.com with the given parameters. \n\nUsage : `!gif your keywords`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String url;
        JSONArray array;
        String query = "";
        for(String arg : args) {
            query += arg.toLowerCase() + "+";
            query = query.substring(0, query.length()-1);
        }
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=dc6zaTOxFJmzC").build();
        try {
            Random rand = new Random();
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            array = json.getJSONArray("data");
            //Random GIF returned by the API
            int gifIndex = rand.nextInt(array.length());
            url = (String) array.getJSONObject(gifIndex).get("url");
            event.getTextChannel().sendMessage(url).queue();
        } catch (IOException | NullPointerException e) {
            event.getTextChannel().sendMessage("No GIF found :cry:").queue();
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
