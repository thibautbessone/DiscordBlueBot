package bluebot.commands.utility;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

/**
 * @file SettingsCommand.java
 * @author Blue
 * @version 0.1
 * @brief Display the currents settings for the server
 */
public class SettingsCommand implements Command {

    private final String HELP = "The command `status` displays the current server settings.\n\nUsage : `!status`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Settings for " + event.getGuild().getName(), "https://bluebot.pw", event.getJDA().getSelfUser().getAvatarUrl());
        builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
        builder.setThumbnail(event.getGuild().getIconUrl());

        //Channels
        String defaultChannel = "`No channel specified`";
        String twitchChannel = MainBot.getTwitchChannel().get(event.getGuild().getId());
        if(twitchChannel == null) {
            twitchChannel = defaultChannel;
        } else {
            twitchChannel = event.getJDA().getTextChannelById(twitchChannel).getAsMention();
        }

        String userEventChannel = MainBot.getUserEventChannel().get(event.getGuild().getId());
        if(userEventChannel == null) {
            userEventChannel = defaultChannel;
        } else {
            userEventChannel = event.getJDA().getTextChannelById(userEventChannel).getAsMention();
        }

        String soundChannel = MainBot.getMusicChannel().get(event.getGuild().getId());
        if(soundChannel == null) {
            soundChannel = defaultChannel;
        } else {
            soundChannel = event.getJDA().getTextChannelById(soundChannel).getAsMention();
        }
        builder.addField("Channels", "Twitch channel : " + twitchChannel + "\nUser events channel : " + userEventChannel + "\nSound channel : " + soundChannel, false);

        //Functionalities
        String twitchNotif = "`enabled`";
        if(MainBot.getTwitchDisabled().contains(event.getGuild().getId())) {
            twitchNotif = "`disabled`";
        }

        String badWords = "`enabled`";
        if(MainBot.getBwDisabled().contains(event.getGuild().getId())) {
            badWords = "`disabled`";
        }

        String userEvents = "`enabled`";
        if(MainBot.getUserEventDisabled().contains(event.getGuild().getId())) {
            userEvents = "`disabled`";
        }

        String serverSb = "`dedicated`";
        if(MainBot.getServerSBDisabled().contains(event.getGuild().getId())) {
            serverSb = "`general`";
        }
        builder.addField("Functionalities : ", "Twitch notifications : " + twitchNotif + "\nBad words filter : " + badWords + "\nUser events : " + userEvents + "\nSoundboard : " + serverSb, false);

        //Autorole
        String autorole = MainBot.getAutoRoleList().get(event.getGuild().getId());
        if(autorole == null) {
            autorole = "`No role specified`";
        } else {
            autorole = event.getJDA().getRolesByName(autorole, true).get(0).getAsMention();
        }
        builder.addField("Autorole : ", autorole, false);

        //Prefix
        String prefix = MainBot.getPrefixes().get(event.getGuild().getId());
        if(prefix == null) {
            prefix = "!";
        }
        builder.addField("Current prefix : ", "Prefix is `" + prefix + "`", false);

        builder.setFooter("To edit these settings, use the channel, enable/disable, setautorole & setprefix commands", "https://i.imgur.com/VHtAEeP.png");

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
