package bluebot.utils;

import bluebot.MainBot;
import bluebot.commands.utility.JoinLeaveMessageContainer;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;

/**
 * @file UserEventsMessagesParser.java
 * @author Blue
 * @version 0.1
 * @brief Allows to customize user events messages with {USER} and {SERVER}
 */
public class UserEventsMessagesParser {

    private JoinLeaveMessageContainer container;

    public UserEventsMessagesParser(GuildMemberJoinEvent event) {
        this.container = MainBot.getUserEventsMessages().get(event.getGuild().getId());
        container.setJoinMessage(container.getJoinMessage().replace("{SERVER}", event.getGuild().getName()));
        if(container.isJoinMention()) {
            container.setJoinMessage(container.getJoinMessage().replace("{USER}", event.getUser().getAsMention()));
        } else {
            container.setJoinMessage(container.getJoinMessage().replace("{USER}", event.getUser().getName() + "#" + event.getUser().getDiscriminator()));
        }
    }

    public UserEventsMessagesParser(GuildMemberLeaveEvent event) {
        this.container = MainBot.getUserEventsMessages().get(event.getGuild().getId());
        container.setLeaveMessage(container.getLeaveMessage().replace("{SERVER}", event.getGuild().getName()));
        if(container.isLeaveMention()) {
            container.setLeaveMessage(container.getLeaveMessage().replace("{USER}", event.getUser().getAsMention()));
        } else {
            container.setLeaveMessage(container.getLeaveMessage().replace("{USER}", event.getUser().getName() + "#" + event.getUser().getDiscriminator()));
        }
    }

    public String getJoinMessage() {return container.getJoinMessage();}
    public String getLeaveMessage() {return container.getLeaveMessage();}
}
