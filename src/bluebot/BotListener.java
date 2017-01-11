package bluebot;

import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 * @file BotListener.java
 * @author Blue
 * @version 0.1
 * @brief Listens to message posted in chat
 */
public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContent().startsWith("!") && event.getMessage().getAuthor() != event.getJDA()) {
            MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContent().toLowerCase(), event));
        }
    }
}
