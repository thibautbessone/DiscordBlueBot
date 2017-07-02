package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.MyUrlPlayer;
import bluebot.utils.Command;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.entities.VoiceChannel;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import net.dv8tion.jda.managers.AudioManager;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @file PlaySoundCommand.java
 * @author Blue
 * @version 0.3
 * @brief Makes the bot join the user current voice channel and play the specified MP3 sound.
 */
public class PlaySoundCommand implements Command {

    //private Map<Guild, MyUrlPlayer> urlPlayersMap = new HashMap<>();
    private final String HELP = "The command `sound` makes the bot join your current voice channel and play the specified sound." +
                                " \n\nUsage : `!sound list` - lists all the available sounds," +
                                " `!sound theSpecifiedSound` - plays the sound," +
                                " `!sound stop` - stop playing and makes the bot leave the voice channel.";
    private File folder = new File("soundboard");
    private Map<Guild, AudioManager> audioManagerMap = new HashMap<>();
    private List<VoiceChannel> voiceChannelList;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(!MainBot.getUrlPlayersMap().containsKey(event.getGuild())) {
            MainBot.getUrlPlayersMap().put(event.getGuild(), new MyUrlPlayer(MainBot.getJda()));
        }
        if(MainBot.getMusicChannel().containsKey(event.getGuild().getId())) {
            if(!event.getTextChannel().getId().equals(MainBot.getMusicChannel().get(event.getGuild().getId()))) {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention()+ ", please use the " + MainBot.getJda().getTextChannelById(MainBot.getMusicChannel().get(event.getGuild().getId())).getAsMention() + " channel for sound commands.");
                return;
            }
        }

        if(args[0].equals("list")) {
        //Displays the sound lists
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
            }
            fileList += "```";
            event.getTextChannel().sendMessage(fileList);

        } else if(args[0].equals("stop")) {
            if(audioManagerMap.get(event.getGuild()).isConnected()) {
                MainBot.getUrlPlayersMap().get(event.getGuild()).reset();
                audioManagerMap.get(event.getGuild()).closeAudioConnection();
            } else {
                event.getTextChannel().sendMessage("I'm not even playing :cry:");
            }
        } else {
            //Play the sound
            if(MainBot.getUrlPlayersMap().get(event.getGuild()).isPlaying()) {
                event.getTextChannel().sendMessage("I'm already playing a sound.");
                return;
            }
            AudioManager audioManager = MainBot.getJda().getAudioManager(event.getGuild());
            audioManagerMap.put(event.getGuild(), audioManager);
            voiceChannelList = audioManagerMap.get(event.getGuild()).getGuild().getVoiceChannels();
            boolean userConnected = false;
            MainBot.getUrlPlayersMap().get(event.getGuild()).reset();

            for(VoiceChannel channel : voiceChannelList) {
                if(channel.getUsers().contains(event.getAuthor())) {
                    userConnected = true;
                    audioManagerMap.get(event.getGuild()).setSendingHandler(MainBot.getUrlPlayersMap().get(event.getGuild()));
                    MainBot.getUrlPlayersMap().get(event.getGuild()).setVolume(1);
                    try {
                        URL file = new File(folder.getName() + "/" + args[0] + ".mp3").toURI().toURL();
                        MainBot.getUrlPlayersMap().get(event.getGuild()).setAudioUrl(file);
                        MainBot.getUrlPlayersMap().get(event.getGuild()).play();
                        if(!audioManagerMap.get(event.getGuild()).isConnected()) {
                            audioManagerMap.get(event.getGuild()).openAudioConnection(channel);
                        }

                    } catch (Exception e) {
                        event.getTextChannel().sendMessage("Incorrect file name, please try again.");
                        return;
                    }
                }
            }
            if(!userConnected) {
                event.getTextChannel().sendMessage("Join a voice channel first.");
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
            event.getTextChannel().sendMessage(help());
        }

    }
}