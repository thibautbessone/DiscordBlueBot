package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import java.util.ArrayList;

/**
 * @file BadWordsListener.java
 * @author Blue
 * @version 0.1
 * @brief Delete messages containing specified bad words and warns the user
 */
public class BadWordsListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            for(String word : MainBot.getBadWords().get(event.getGuild())) {
                if(event.getMessage().getContent().contains(word)) {
                    event.getMessage().deleteMessage();
                    event.getTextChannel().sendMessage("Whoop whoop " + event.getMessage().getAuthor().getAsMention() + ", don't use such bad words :confused:");
                    break;
                }
            }
        } catch (NullPointerException e) { //Guild is not present in the badWords map
            MainBot.getBadWords().put(event.getGuild(), new ArrayList<String>());
        }
    }
}
