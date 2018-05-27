package bluebot.commands.utility;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

/**
 * @file PingCommand.java
 * @author Blue
 * @version 0.3
 * @brief Classic ping command
 */
public class PingCommand implements Command {

    private final String HELP = "The command `ping` pings the bot (to check if it's online). \nUsage : `!ping`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
        builder.setAuthor("I'm online !");
        builder.setDescription("Ping : " + event.getJDA().getPing() + " ms");

        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            event.getTextChannel().sendMessage(help()).queue();
        }
    }
}
