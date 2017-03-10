package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.MyUrlPlayer;
import bluebot.utils.Command;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.managers.AudioManager;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @file PlaySoundCommand.java
 * @author Blue
 * @version 0.1
 * @brief Makes the bot join the user current voicechannel and play the specified MP3 sound.
 */
public class PlaySoundCommand implements Command {

    private MyUrlPlayer urlPlayer = new MyUrlPlayer(MainBot.getJda());
    private final String HELP = "The command `sound` makes the bot join your current voicechannel and play the specified sound. \n\nUsage : `!sound list` - lists all the available sounds, `!sound theSpecifiedSound` - plays the sound";
    private File folder = new File("soundboard");
    private File[] listOfFiles;
    private ArrayList<String> list = new ArrayList<>();

    private AudioManager audioManager;
    private List<VoiceChannel> voiceChannelList;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        } else if(args[0].equals("list")) {
            listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if(file.getName().contains(".mp3")) {
                    list.add(file.getName().replace(".mp3", ""));
                    System.out.println(file.getName().replace(".mp3", ""));
                } else continue;

            }

        } else if(args[0].equals("stop")) {
            if(urlPlayer.isPlaying()) {
                urlPlayer.reset();
                audioManager.closeAudioConnection();
            } else {
                event.getTextChannel().sendMessage("I'm not even playing :cry:");
            }

        } else {
            if(urlPlayer.isPlaying()) {
                event.getTextChannel().sendMessage("I'm already playing a sound.");
                return;
            }
            audioManager = MainBot.getJda().getAudioManager(event.getGuild());
            voiceChannelList = audioManager.getGuild().getVoiceChannels();

            for(VoiceChannel channel : voiceChannelList) {
                if(channel.getUsers().contains(event.getAuthor())) {
                    audioManager.setSendingHandler(urlPlayer);
                    urlPlayer.setVolume(1);
                    try {
                        URL file = new File(folder.getName() + "/" + args[0] + ".mp3").toURI().toURL();
                        urlPlayer.setAudioUrl(file);
                        urlPlayer.play();
                        audioManager.openAudioConnection(channel);
                    } catch (Exception e ) {
                        event.getTextChannel().sendMessage("Incorrect file name, please try again.");
                        return;
                    }
                }
            }
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}