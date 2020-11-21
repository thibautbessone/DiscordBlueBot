package bluebot.commands.misc;

import bluebot.utils.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

/**
 * @file InviteCommand.java
 * @author Blue
 * @version 0.1
 * @brief Make the bot posts invite and website links.
 */
public class InviteCommand implements Command {

    private final String HELP = "The command `invite` posts BlueBot's invite and website links, . \nUsage : `!invite`";
    private final String INVITE = "https://discordapp.com/oauth2/authorize?client_id=268420199370194944&scope=bot&permissions=-1";
    private final String WEBSITELINK = "https://bluebot.pw/";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        event.getTextChannel().sendMessage( "Invite the official instance of BlueBot : " + INVITE +"\nWebsite : " + WEBSITELINK).queue();
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
