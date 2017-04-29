package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 * @file BotKickedListener.java
 * @author Blue
 * @version 0.1
 * @brief Detects when the bot is kicked from a server to prevent malfunctions. Created after a bug report made by sinikebe#2208
 */
public class BotKickedListener extends ListenerAdapter {
    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        MainBot.getUrlPlayersMap().remove(event.getGuild());
        System.out.println("Bot kicked from " + event.getGuild().getName() + "(" + event.getGuild().getId() + ")");
    }
}
