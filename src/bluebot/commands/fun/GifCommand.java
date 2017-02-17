package bluebot.commands.fun;

import bluebot.utils.Command;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.Random;

/**
 * @file GifCommand.java
 * @author Blue
 * @version 0.1
 * @brief Makes the bot post a gif searched on Giphy with the specified keywords
 */
public class GifCommand implements Command {

    private final String HELP = "The command `gif` posts the first search result from giphy.com with the given parameters. \n\nUsage : `!gif your keywords`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        } else {
            String url;
            JSONArray array;
            String query = new String();
            for(String arg : args) {
                query += arg.toLowerCase() + "+";
                query = query.substring(0, query.length()-1);
            }
            try {
                Random rand = new Random();

                //First gif returned by the API
                //url = (String )Unirest.get("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=dc6zaTOxFJmzC").asJson().getBody().getObject().getJSONArray("data").getJSONObject(0).get("url");
                array = Unirest.get("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=dc6zaTOxFJmzC").asJson().getBody().getObject().getJSONArray("data");
                int gifIndex = rand.nextInt(array.length());
                url = (String) array.getJSONObject(gifIndex).get("url");
                event.getTextChannel().sendMessage(url);
            } catch (Exception e) {
                event.getTextChannel().sendMessage("No GIF found :cry:");
            }
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
