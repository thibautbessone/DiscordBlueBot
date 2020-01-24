package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.log4j.Logger;

/**
 * @file GuildsListener.java
 * @author Blue
 * @version 0.1
 * @brief Detects when the bot is kicked from a server to prevent malfunctions. Created after a bug report made by sinikebe#2208
 */
public class GuildsListener extends ListenerAdapter {

    private static Logger logger = Logger.getLogger(GuildsListener.class);

    private TextChannel retrieveLogChannel() {
        for(JDA shard : MainBot.getJdaList()) {
            if(shard.getTextChannelById(MainBot.getConfig().getGuildsLogChannelId()) != null) {
                return shard.getTextChannelById(MainBot.getConfig().getGuildsLogChannelId());
            }
        }
        return null;
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        String addMsg = "Added on server **" +  event.getGuild().getName() + "** (" + event.getGuild().getMembers().size() + " users)" +
                "\n ID : " + event.getGuild().getId() + ", Owner : " + event.getGuild().getOwner().getAsMention();
        retrieveLogChannel().sendMessage(addMsg).queue();
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        String rmvMsg = "Removed from server **" +  event.getGuild().getName() + "** (" + event.getGuild().getMembers().size() + " users)" +
                "\n ID : " + event.getGuild().getId() + ", Owner : " + event.getGuild().getOwner().getAsMention();
        retrieveLogChannel().sendMessage(rmvMsg).queue();
        // Deletes prefix to avoid malfunctions - thanks to sinikebe#2208 for reporting the issue
        MainBot.getPrefixes().remove(event.getGuild().getId());
        logger.info("Bot kicked from " + event.getGuild().getName() + "(" + event.getGuild().getId() + ")");
    }
}
