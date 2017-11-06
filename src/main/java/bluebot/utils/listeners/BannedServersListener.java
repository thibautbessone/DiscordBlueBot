package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


/**
 * @file BannedServersListener.java
 * @author Blue
 * @version 0.1
 * @brief Make the bot leave the server. Created due to user creating "spam-servers" to overload small bots.
 */
public class BannedServersListener extends ListenerAdapter {



    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(MainBot.getBannedServers().containsKey(event.getGuild().getId())) {
            event.getGuild().leave().queue();
        } //leaves the banned server
    }
}
