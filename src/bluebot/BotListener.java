package bluebot;

import net.dv8tion.jda.events.ReadyEvent;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 * Created by Thibaut on 11/01/2017.
 */
public class BotListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContent().startsWith("!") && event.getMessage().getAuthor() != event.getJDA()) {
            MainBot.handleCommand(MainBot.parser.parse(event.getMessage().getContent().toLowerCase(), event));
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        //MainBot.log("status", "Logged in as " + event.getJDA().getSelfInfo().getUsername());
    }


}
