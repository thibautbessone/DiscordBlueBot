package bluebot.commands.fun;

import bluebot.utils.Command;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONException;

/**
 * Created by Thibaut on 17/01/2017.
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
            String query = new String();
            for(String arg : args) {
                query += arg.toLowerCase() + "+";
                query = query.substring(0, query.length()-1);
            }
            try {
                url = (String )Unirest.get("http://api.giphy.com/v1/gifs/search?q=" + query + "&api_key=dc6zaTOxFJmzC").asJson().getBody().getObject().getJSONArray("data").getJSONObject(0).get("url");
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
