package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

/**
 * @file MessageReceivedListener.java
 * @author Blue
 * @version 0.1
 * @brief Listens to message posted in chat
 */
public class MessageReceivedListener extends ListenerAdapter {

    private static Logger logger = Logger.getLogger(MessageReceivedListener.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            if(!event.getAuthor().isBot()) {
                if (MainBot.getPrefixes().containsKey(event.getGuild().getId())) {
                    if(event.getMessage().getContentRaw().startsWith(MainBot.getPrefixes().get(event.getGuild().getId())) && event.getMessage().getAuthor() != event.getJDA()) {
                        MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContentRaw(), event));
                    }
                } else {
                    if(event.getMessage().getContentRaw().startsWith(MainBot.getBasePrefix()) && event.getMessage().getAuthor() != event.getJDA()) {
                        MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContentRaw(), event));
                    }
                }
                logger.info("[" + LocalDateTime.now() + "] " + "Guild : " + event.getGuild().getName() + "/" + event.getAuthor().getName() + "(" + event.getMessage().getAuthor().getId() + ")" + " : " + event.getMessage().getContentRaw());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            //Command sent in private channel with the bot
            if(event.getMessage().getContentRaw().startsWith(MainBot.getBasePrefix()) && event.getMessage().getAuthor() != event.getJDA()) {
                event.getPrivateChannel().sendMessage("You can't use commands in our private conversation :wink:").queue();
            }
        }
    }
}
