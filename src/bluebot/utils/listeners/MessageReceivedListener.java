package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

import java.time.LocalDateTime;

/**
 * @file MessageReceivedListener.java
 * @author Blue
 * @version 0.1
 * @brief Listens to message posted in chat
 */
public class MessageReceivedListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        try {
            if(!event.getAuthor().getId().equals(MainBot.getJda().getSelfInfo().getId())) {
                if (MainBot.getPrefixes().containsKey(event.getGuild())) {
                    if(event.getMessage().getContent().startsWith(MainBot.getPrefixes().get(event.getGuild())) && event.getMessage().getAuthor() != event.getJDA()) {
                        MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContent(), event));
                    }
                } else {
                    if(event.getMessage().getContent().startsWith(MainBot.getBasePrefix()) && event.getMessage().getAuthor() != event.getJDA()) {
                        MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContent(), event));
                    }
                }
                System.out.println(MainBot.getBasePrefix());
                System.out.println("[" + LocalDateTime.now() + "] " + "Guild : " + event.getGuild().getName() + "/" + event.getAuthor().getUsername() + "(" + event.getMessage().getAuthor().getId() + ")" + " : " + event.getMessage().getContent());
            }
        } catch (NullPointerException e) {
            //Command sent in private channel with the bot
            if(event.getMessage().getContent().startsWith(MainBot.getBasePrefix()) && event.getMessage().getAuthor() != event.getJDA()) {
                event.getPrivateChannel().sendMessage("You can't use commands in our private conversation :wink:");
            }
        }
    }
}
