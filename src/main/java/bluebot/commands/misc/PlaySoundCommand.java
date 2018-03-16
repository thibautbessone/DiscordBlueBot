package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import bluebot.utils.soundUtils.AudioPlayerSendHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.io.File;
import java.util.ArrayList;


/**
 * @file PlaySoundCommand.java
 * @author Blue
 * @version 0.4
 * @brief Makes the bot join the user current voice channel and play the specified MP3 sound.
 */

public class PlaySoundCommand implements Command {

    private final String HELP = "The command `sound` makes the bot join your current voice channel and play the specified sound." +
                                " \n\nUsage : `!sound list` - lists all the available sounds on your server," +
                                " `!sound theSpecifiedSound` - plays the sound," +
                                " `!sound stop` - stop playing and makes the bot leave the voice channel." +
                                " \nYou can toggle between your server soundboard and the general soundboard using the `!enable` and `!disable` commands.";
    private File folder;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        System.out.println(MainBot.getServerSBDisabled().toString());
        if(MainBot.getServerSBDisabled().contains(event.getGuild().getId())) {
            //folder = new File("soundboard/");
            event.getTextChannel().sendMessage("Full sound list available at : https://bluebot.pw/sound_list.php").queue();
            return; //Edited since 4500+ sounds were a mess to list lol

        } else {
            folder = new File("soundboard" + "/" + event.getGuild().getId());
        }

        AudioManager guildAudioManager = event.getGuild().getAudioManager();
        AudioPlayer player = MainBot.getPlayerManager().createPlayer();
        //TrackScheduler scheduler = new TrackScheduler(player);
        //player.addListener(scheduler);

        if(MainBot.getMusicChannel().containsKey(event.getGuild().getId())) {
            if(!event.getTextChannel().getId().equals(MainBot.getMusicChannel().get(event.getGuild().getId()))) {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention()+ ", please use the " + MainBot.getJda().getTextChannelById(MainBot.getMusicChannel().get(event.getGuild().getId())).getAsMention() + " channel for sound commands.").queue();
                return;
            }
        }

        if(args[0].equals("list")) {
        //Displays the sound lists LENGTH : 15
            ArrayList<String> list = new ArrayList<>();
            for (File file : folder.listFiles()) {
                if(file.getName().contains(".mp3")) {
                    list.add(file.getName().replace(".mp3", ""));
                }
            }

            String fileList = "```Available sounds :\n" +
                                 "------------------\n\n";
            int nb = 0;
            for(String file : list) {
                nb++;
                while(file.length() < 15) {
                    file += " ";
                }
                fileList += file + "\t";
                if(0 == nb%6) fileList += "\n";
                if(0 == nb%42) {
                    System.out.println("50 atteint");
                    fileList += "```";
                    event.getTextChannel().sendMessage(fileList).queue();
                    fileList = "```";
                }
            }
            fileList += "```";
            event.getTextChannel().sendMessage(fileList).queue();

        } else if(args[0].equals("stop")) {
            try {
                guildAudioManager.setSendingHandler(null);
                guildAudioManager.closeAudioConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            VoiceChannel targetChannel = event.getMember().getVoiceState().getChannel();
            if (targetChannel == null) {
                event.getTextChannel().sendMessage("Please join a voice channel first.").queue();
            } else {
                //playing the sound
                final String trackUrl = "./" + folder.getPath() + "/" + args[0] + ".mp3";
                MainBot.getPlayerManager().loadItem(trackUrl, new AudioLoadResultHandler() {
                    @Override
                    public void trackLoaded(AudioTrack audioTrack) {
                        AudioPlayerSendHandler handler = new AudioPlayerSendHandler(player);
                        guildAudioManager.setSendingHandler(handler);
                        guildAudioManager.openAudioConnection(targetChannel);

                        player.playTrack(audioTrack);
                    }

                    @Override
                    public void playlistLoaded(AudioPlaylist audioPlaylist) {
                        //unusued
                    }

                    @Override
                    public void noMatches() {
                        event.getTextChannel().sendMessage("The sound **" + args[0] + "** doesn't exist.").queue();
                    }

                    @Override
                    public void loadFailed(FriendlyException e) {
                        e.printStackTrace();
                        event.getTextChannel().sendMessage("Something unexpected happened, I couldn't play the sound.").queue();
                    }
                });
            }
        }
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
