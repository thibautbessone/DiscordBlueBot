package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.user.update.UserUpdateActivityOrderEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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

    public void onUserUpdateGame(UserUpdateActivityOrderEvent event) {
        try {
            if(MainBot.getStreamerList().containsKey(event.getUser().getId()) && !event.getOldValue().get(0).getType().equals(Activity.ActivityType.STREAMING) && event.getGuild().getMember(event.getUser()).getActivities().get(0).getType().equals(Activity.ActivityType.STREAMING)) {
                List<Guild> serverList = event.getJDA().getGuilds();
                for(Guild server : serverList) {
                    if(server.getMembers().contains(event.getUser())) {
                        if(MainBot.getTwitchDisabled().contains(server.getId())) {
                            continue; //function disabled
                        }
                        if(MainBot.getTwitchChannel().containsKey(server.getId())) {
                            event.getJDA().getTextChannelById(MainBot.getTwitchChannel().get(server.getId())).sendMessage(/*server.getPublicRole().getName() + " : " + */event.getUser().getName() + " is now streaming ! Watch live at " + MainBot.getStreamerList().get(event.getUser().getId())).queue();
                        } else {
                            server.getDefaultChannel().sendMessage(/*server.getPublicRole().getName() + " : " + */event.getUser().getName() + " is now streaming ! Watch live at " + MainBot.getStreamerList().get(event.getUser().getId())).queue();
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace(); //The user left streaming
        }
    }
}