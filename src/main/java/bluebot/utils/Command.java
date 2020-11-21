package bluebot.utils;


import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * @file Command.java
 * @author Blue
 * @version 0.1
 * @brief The Command interface, which will be implemented by all the future commands
 */
public interface Command {
    public boolean called(String[] args, MessageReceivedEvent event);
    public void action(String[] args, MessageReceivedEvent event);
    public String help();
    public void executed(boolean success, MessageReceivedEvent event);
}
