package bluebot;

import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * Created by Thibaut on 11/01/2017.
 */
public interface Command {
    public boolean called(String[] args, MessageReceivedEvent event);
    public void action(String[] args, MessageReceivedEvent event);
    public String help();
    public void executed(boolean success, MessageReceivedEvent event);
}
