package bluebot.utils;

import bluebot.MainBot;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
import java.time.LocalDateTime;

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
            MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContent(), event));
        }

        System.out.println("[" + LocalDateTime.now() + "] " + "Guild : " + event.getGuild().getName() + "/" + event.getAuthor().getUsername() + "(" + event.getMessage().getAuthor().getId() + ")" + " : " + event.getMessage().getContent());
    }
}
