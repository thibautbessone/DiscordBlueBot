package bluebot.utils.listeners;

import bluebot.MainBot;
import bluebot.utils.LoadingProperties;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;


/**
 * @file CleverbotListener.java
 * @author Blue
 * @version 0.1
 * @brief Let the user interact with the bot using Clever's bots API : https://www.cleverbot.com/api/
 */
public class CleverbotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(MainBot.getCleverbotDisabled().contains(event.getGuild().getId())) {
            return; //function disabled
        }
        try {
            if(event.getAuthor().getId() != MainBot.getJda().getSelfInfo().getId()) {
                boolean talkingToBot = false;
                for(User mention : event.getMessage().getMentionedUsers()) {
                    if (mention.getId().equals(event.getJDA().getSelfInfo().getId())) talkingToBot = true;
                }

                //The bot is mentioned
                if (talkingToBot) {

                    //Creating the query
                    String message = event.getMessage().getContent();
                    message = message.replaceAll("@\\p{L}+", ""); //deletes the mentions
                    String text = message.replace(' ', '+');//creates the query
                    text = text.substring(0, text.length()-1);

                    LoadingProperties config = new LoadingProperties();
                    String query = ("http://www.cleverbot.com/getreply?key=" + config.getCleverbotAPIKey() + "&input=" + text);

                    //Handling the response
                    try {
                        String answer = (String) Unirest.get(query).asJson().getBody().getObject().get("output");
                        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " " + answer);
                    } catch (UnirestException e) {
                        event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + " I'm currently unable to answer your question. I might be offline at the moment.");
                    }
                }
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
