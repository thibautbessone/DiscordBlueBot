package bluebot.utils.listeners;

import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.guild.voice.GenericGuildVoiceEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

import java.time.LocalDateTime;

/**
 * @file EmptyVCListener.java
 * @author Blue
 * @version 0.1
 * @brief Listens to message posted in chat
 */
public class EmptyVCListener extends ListenerAdapter {

    private static Logger logger = Logger.getLogger(MessageReceivedListener.class);

    @Override
    public void onGenericGuildVoice(GenericGuildVoiceEvent event) {
        if(event instanceof GuildVoiceMoveEvent || event instanceof GuildVoiceLeaveEvent) {
            if(!event.getMember().getUser().isBot() && event.getGuild().getAudioManager().isConnected() && event.getGuild().getAudioManager().getConnectedChannel().getMembers().size() == 1) { // aka only bluebot
                VoiceChannel channel = event.getGuild().getAudioManager().getConnectedChannel();
                event.getGuild().getAudioManager().closeAudioConnection();
                logger.info("[" + LocalDateTime.now() + "] " + "Guild : " + event.getGuild().getName() + "/" + channel.getName() + " is now empty, disconnecting");
            }
        }
    }
}
