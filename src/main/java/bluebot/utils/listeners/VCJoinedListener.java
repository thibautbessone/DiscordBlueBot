package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @file VCJoinedListener.java
 * @author Blue
 * @version 0.1
 * @brief Allows the bot to play a sound when a user joins the current voice channel
 */

public class VCJoinedListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        System.out.println((event.getChannelJoined().getMembers()));
        if (event.getChannelJoined().getMembers().contains(event.getGuild().getMemberById(MainBot.getJda().getSelfUser().getId()))) {
            System.out.println("detected");
        }
    }
}
