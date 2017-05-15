package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.Permission;
import net.dv8tion.jda.entities.TextChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.utils.PermissionUtil;

/**
 * @file SpecificChannelCommand.java
 * @author Blue
 * @version 0.1
 * @brief Set the channels for Twitch notifications, user events (join/leave) & sound commands
 */
public class SpecificChannelCommand implements Command {

    private final String HELP = "The command `ch` sets the restricted channel used by the bot for Twitch notifications / Sound commands / User events. " +
            "\nThis command requires the manage messages permission." +
            "\n\nUsage : `!ch yourChannel twitch | userevent | sound | soundReset`";

    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getGuild(), event.getAuthor(), Permission.MESSAGE_MANAGE)) {
                return true;
            } else {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you don't have the permission to do that.");
                permissionFail = true;
                return false;
            }
        }
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        boolean channelExists = false;
        for(TextChannel ch : event.getGuild().getTextChannels()) {
            if(ch.getName().equals(args[0])) channelExists = true;
        }
        if(!channelExists) {
            event.getTextChannel().sendMessage("The specified channel doesn't exist.");
        } else {
            TextChannel channel = event.getGuild().getPublicChannel(); //default channel
            for(TextChannel ch : event.getGuild().getTextChannels()) {
                if(ch.getName().equals(args[0])) channel = ch;
            }
            switch (args[1]) {
                case "twitch":
                    MainBot.getTwitchChannel().put(event.getGuild(), channel);
                    event.getTextChannel().sendMessage("Channel " + channel.getAsMention() + " set for Twitch notifications.");
                    System.out.println(MainBot.getTwitchChannel());
                    break;
                case "userevent":
                    MainBot.getUsereventChannel().put(event.getGuild(), channel);
                    event.getTextChannel().sendMessage("Channel " + channel.getAsMention() + " set for user event messages.");
                    break;
                case "sound":
                    //Prevents restricting sound command usage to general channel
                    if (!channel.getId().equals(event.getGuild().getPublicChannel().getId())) {
                        MainBot.getMusicChannel().put(event.getGuild(), channel);
                        event.getTextChannel().sendMessage("Channel " + channel.getAsMention() + " set for sound commands.");
                    }
                    break;
                case "soundReset":
                    if(MainBot.getMusicChannel().containsKey(event.getGuild())) {
                        MainBot.getMusicChannel().remove(event.getGuild());
                    }
                    event.getTextChannel().sendMessage("You can now use the sound commands on any channel.");
                    break;
                default:
                    event.getTextChannel().sendMessage("Incorrect option.");
                    break;
            }
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if (!success) {
            if(!permissionFail) {
                event.getTextChannel().sendMessage(help());
            }
            permissionFail = false;
        }
    }
}
