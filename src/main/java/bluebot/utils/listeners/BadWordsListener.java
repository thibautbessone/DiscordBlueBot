package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
        if(MainBot.getBwDisabled().contains(event.getGuild().getId())) {
            return; //function disabled
        }
        if(event.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) return; // avoid getting triggered by the bots own messages
        try {
            for(String word : MainBot.getBadWords().get(event.getGuild().getId())) {
                if(event.getMessage().getContentRaw().contains(word)) {
                    event.getMessage().delete().queue();
                    event.getTextChannel().sendMessage("Whoop whoop " + event.getMessage().getAuthor().getAsMention() + ", don't use such bad words :confused:").queue();
                    break;
                }
            }
        } catch (NullPointerException e) { //Guild is not present in the badWords map
            MainBot.getBadWords().put(event.getGuild().getId(), new ArrayList<String>());
        }
    }
}
