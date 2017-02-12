package bluebot.commands;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.managers.AudioManager;

import java.util.List;

/**
 * Created by Thibaut on 17/01/2017.
 */
public class PlaySoundCommand implements Command {
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        AudioManager audioManager = MainBot.getJda().getAudioManager(event.getGuild());
        List<VoiceChannel> listeVoiceChannels = audioManager.getGuild().getVoiceChannels();
        for(VoiceChannel channel : listeVoiceChannels) {
            if(channel.getUsers().contains(event.getAuthor())) {
                audioManager.openAudioConnection(channel);
                //TODO later

            }
        }

    }

    @Override
    public String help() {
        return null;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}