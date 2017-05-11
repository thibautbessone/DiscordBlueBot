package bluebot.utils.listeners;

import bluebot.MainBot;
import net.dv8tion.jda.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.hooks.ListenerAdapter;

/**
 * @file UserJoinLeaveListener.java
 * @author Blue
 * @version 0.1
 * @brief Make the bot say a welcome/goodbye message when an user joins/leaves the server, and automatically gives the user the specified role on join
 */
public class UserJoinLeaveListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if(MainBot.getUserEventDisabled().contains(event.getGuild().getId())) {
            return; //function disabled
        }
        event.getGuild().getPublicChannel().sendMessage(event.getUser().getAsMention() + " has joined the server ! Welcome :wave: !");
        event.getGuild().getManager().addRoleToUser(event.getUser(), event.getGuild().getRolesByName(MainBot.getAutoRoleList().get(event.getGuild().getId())).get(0)).update();
    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        if(MainBot.getUserEventDisabled().contains(event.getGuild().getId())) {
            return; //function disabled
        }
        event.getGuild().getPublicChannel().sendMessage(event.getUser().getAsMention() + " has left the server :cry: !");
    }
}
