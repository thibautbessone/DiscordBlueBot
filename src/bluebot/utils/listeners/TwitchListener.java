package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.entities.Game;
import net.dv8tion.jda.entities.Guild;
import net.dv8tion.jda.events.user.UserGameUpdateEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;
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
            if(MainBot.getStreamerList().containsKey(event.getUser().getId()) && event.getPreviousGame().getType().equals(Game.GameType.DEFAULT) && event.getUser().getCurrentGame().getType().equals(Game.GameType.TWITCH)) {
                List<Guild> serverList = MainBot.getJda().getGuilds();
                for(Guild server : serverList) {
                    if(server.getUsers().contains(event.getUser())) {
                        server.getPublicChannel().sendMessage(server.getPublicRole().getName() + " : " + event.getUser().getUsername() + " is now streaming ! Watch live at " + MainBot.getStreamerList().get(event.getUser().getId()));
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace(); //The user left streaming
        }
    }
}