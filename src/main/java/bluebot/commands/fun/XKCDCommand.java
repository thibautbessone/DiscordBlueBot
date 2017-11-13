package bluebot.commands.fun;

import bluebot.utils.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

/**
 * @file XKCDCommand.java
 * @author Blue
 * @version 0.1
 * @brief Posts a comic from xkcd.com
 */
public class XKCDCommand implements Command {

    private final String HELP = "The command `xkcd` posts a Xkcd comic from xkcd.com. \n\nUsage : `!xkcd latest` - posts the latest comic, `!xkcd random` - posts a random comic";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String url = new String();
        OkHttpClient caller = new OkHttpClient();
        Request request;

        if(args[0].equals("latest")) {
            request = new Request.Builder().url("http://xkcd.com/info.0.json ").build();
        } else if(args[0].equals("random")) {
            Random r = new Random();
            int result = r.nextInt(1915);
            request = new Request.Builder().url("http://xkcd.com/" + result + "/info.0.json ").build();
        } else {
            event.getTextChannel().sendMessage(help()).queue();
            return;        }

        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());
            url = (String) json.get("img");
        } catch (IOException | NullPointerException e) {
            event.getTextChannel().sendMessage("The xkcd.com API might be down at the moment").queue();
        }

        event.getTextChannel().sendMessage(url).queue();

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
