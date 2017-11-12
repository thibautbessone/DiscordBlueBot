package bluebot.commands.fun;

import bluebot.utils.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @file CatCommand.java
 * @author Blue
 * @version 0.1
 * @brief Posts a random dog image/gif from https://dog.ceo
 */
public class DogCommand implements Command {

    private final String HELP = "The command `dog` posts a cat image from dog.ceo.\n\nUsage : `!dog`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String url = new String();
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("https://dog.ceo/api/breeds/image/random").build();
        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            url = (String) json.get("message");
        } catch (IOException | NullPointerException e) {
            event.getTextChannel().sendMessage("The random.cat API might be down at the moment").queue();
        }
        event.getTextChannel().sendMessage(url).queue();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
