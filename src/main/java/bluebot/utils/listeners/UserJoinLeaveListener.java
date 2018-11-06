package bluebot.utils.listeners;

import bluebot.MainBot;
import bluebot.utils.UserEventsMessagesParser;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

/**
 * @file UserJoinLeaveListener.java
 * @author Blue
 * @version 0.1
 * @brief Make the bot say a welcome/goodbye message when an user joins/leaves the server, and automatically gives the user the specified role on join
 */
public class UserJoinLeaveListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {

        //Welcome
        if(MainBot.getUserEventDisabled().contains(event.getGuild().getId())) {
            return; //function disabled
        }
        if(MainBot.getUserEventChannel().containsKey(event.getGuild().getId())) {
            if(MainBot.getUserEventsMessages().get(event.getGuild().getId()) != null) {
                UserEventsMessagesParser parser = new UserEventsMessagesParser(event);
                event.getJDA().getTextChannelById(MainBot.getUserEventChannel().get(event.getGuild().getId())).sendMessage(parser.getJoinMessage()).queue();
            } else {
                //Default message in specified channel
                event.getJDA().getTextChannelById(MainBot.getUserEventChannel().get(event.getGuild().getId())).sendMessage(event.getMember().getAsMention() + " has joined the server ! Welcome :wave: !").queue();
            }
        } else {
            //Default behavior
            event.getGuild().getDefaultChannel().sendMessage(event.getMember().getAsMention() + " has joined the server ! Welcome :wave: !").queue();
        }
        //event.getGuild().getManager().addRoleToUser(event.getMember(), event.getGuild().getRolesByName(MainBot.getAutoRoleList().get(event.getGuild().getId())).get(0)).update();
        event.getGuild().getController().addRolesToMember(event.getMember(), event.getGuild().getRolesByName(MainBot.getAutoRoleList().get(event.getGuild().getId()), true).get(0)).queue();

        //Name protection
        if(MainBot.getNameProtectionDisabled().contains(event.getGuild().getId())) {
            return; //function disabled
        }
        /*//Check if the newcomers name already exists
        int lookalikes = 0;
        for(net.dv8tion.jda.core.entities.Member member : event.getGuild().getMembers()) {
            if(member.getEffectiveName().equals(event.getMember().getEffectiveName())) {
                ++lookalikes;
                event.getGuild().getController().setNickname(event.getMember(), event.getMember().getEffectiveName() + "(" + lookalikes + ")").queue();
            }
        }*/

    }

    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {

        //Default behavior
        if(MainBot.getUserEventDisabled().contains(event.getGuild().getId())) {
            return; //function disabled
        }
        if(MainBot.getUserEventChannel().containsKey(event.getGuild().getId())) {
            if(MainBot.getUserEventsMessages().get(event.getGuild().getId()) != null) {
                UserEventsMessagesParser parser = new UserEventsMessagesParser(event);
                event.getJDA().getTextChannelById(MainBot.getUserEventChannel().get(event.getGuild().getId())).sendMessage(parser.getLeaveMessage()).queue();
            } else {
                event.getJDA().getTextChannelById(MainBot.getUserEventChannel().get(event.getGuild().getId())).sendMessage(event.getMember().getAsMention() + " has left the server :cry: !").queue();
            }
        } else {
            event.getGuild().getDefaultChannel().sendMessage(event.getUser().getAsMention() + " has left the server :cry: !").queue();
        }
    }
}
