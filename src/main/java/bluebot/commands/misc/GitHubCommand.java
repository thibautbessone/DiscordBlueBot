package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;

/**
 * @file GitHubCommand.java
 * @author Blue
 * @version 0.1
 * @brief Post information about the specified GitHub user
 */
public class GitHubCommand implements Command {

    private final String HELP = "The command `github` provides information on the GitHub specified user.\n\nUsage : `!github username`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/users/" + args[0]).build();
        EmbedBuilder builder = new EmbedBuilder();

        try {
            Response response = caller.newCall(request).execute();
            JSONObject json = new JSONObject(response.body().string());

            String pseudonym = json.getString("name");
            String bio = "None";
            String location = "Unknown";
            String website = "None";
            try {
                bio = json.getString("bio");
            } catch (JSONException e) {
                //no bio
            }
            try {
                location = json.getString("location");
            } catch (JSONException e) {
                //no known location
            }

            if(!json.getString("blog").equals("")) website = json.getString("blog");

            builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
            builder.setAuthor("Information about " + pseudonym + " (" + args[0] + ")", "https://github.com/" + args[0] + "", "http://i.imgur.com/pH59eAC.png");
            builder.setThumbnail(json.getString("avatar_url"));

            builder.addField("User bio", bio, false);
            builder.addField("Location", location, true);
            builder.addField("Website", website, true);
            builder.addField("Public repositories", String.valueOf(json.getInt("public_repos")), true);
            builder.addField("Public gists", "" + String.valueOf(json.getInt("public_gists")), true);

            builder.addField("Followers", "" + String.valueOf(json.getInt("followers")), true);
            builder.addField("Following", "" + String.valueOf(json.getInt("following")), true);

            event.getTextChannel().sendMessage(builder.build()).queue();
        } catch (IOException | NullPointerException e) {
            event.getTextChannel().sendMessage("The GitHub API might be down at the moment").queue();
        } catch (JSONException e) {
            event.getTextChannel().sendMessage("User not found.").queue();
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
