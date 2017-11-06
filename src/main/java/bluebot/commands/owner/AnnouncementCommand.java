package bluebot.commands.owner;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

/**
 * @file AnnouncementCommand.java
 * @author Blue
 * @version 0.1
 * @brief Allows the owner of the bot to say a message on all the server the bot is connected on.
 */
public class AnnouncementCommand implements Command {

    private final String HELP = "The command `announcement` makes the bot say a message on all its servers." +
            "\nThis command requires to be the owner of the bot." +
            "\n\nUsage : `!announcement yourMessage`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            return false;
        } else {
            if(event.getAuthor().getId().equals(MainBot.getBotOwner())) {
                return true;
            } else {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you don't have the permission to do that.").queue();
                permissionFail = true;
                return false;
            }
        }
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String text = "**Announcement :** ";
        for(String arg : args) {
            text += arg + " ";
        }
        for(Guild guild : MainBot.getJda().getGuilds()) {
            if(guild == event.getGuild()) continue;
            guild.getPublicChannel().sendMessage(text).queue();
        }
        event.getTextChannel().sendMessage("Announcement done.").queue();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            if(!permissionFail) {
                event.getTextChannel().sendMessage(help()).queue();
            }
            permissionFail = false;
        }
    }
}
