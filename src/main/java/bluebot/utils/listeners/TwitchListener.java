package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.user.UserGameUpdateEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.util.List;

/**
 * @file TwitchListener.java
 * @author Blue
 * @version 0.2
 * @brief Make the bot notify the users when a tracked streamer starts streaming on Twitch
 */


public class TwitchListener extends ListenerAdapter {

    public TwitchListener() {
        MainBot.getStreamerList().put("218461869617184768", "https://www.twitch.tv/minipasglop"); //Id Minipasglop for test : twitch.tv/minipasglop
    }

    public void onUserGameUpdate(UserGameUpdateEvent event) {
        try {
            if(MainBot.getStreamerList().containsKey(event.getUser().getId()) && !event.getPreviousGame().getType().equals(Game.GameType.STREAMING) && event.getGuild().getMember(event.getUser()).getGame().getType().equals(Game.GameType.STREAMING)) {
                List<Guild> serverList = MainBot.getJda().getGuilds();
                for(Guild server : serverList) {
                    if(server.getMembers().contains(event.getUser())) {
                        if(MainBot.getTwitchDisabled().contains(server.getId())) {
                            continue; //function disabled
                        }
                        if(MainBot.getTwitchChannel().containsKey(server.getId())) {
                            MainBot.getJda().getTextChannelById(MainBot.getTwitchChannel().get(server.getId())).sendMessage(/*server.getPublicRole().getName() + " : " + */event.getUser().getName() + " is now streaming ! Watch live at " + MainBot.getStreamerList().get(event.getUser().getId())).queue();
                        } else {
                            server.getPublicChannel().sendMessage(/*server.getPublicRole().getName() + " : " + */event.getUser().getName() + " is now streaming ! Watch live at " + MainBot.getStreamerList().get(event.getUser().getId())).queue();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace(); //The user left streaming
        }
    }
}