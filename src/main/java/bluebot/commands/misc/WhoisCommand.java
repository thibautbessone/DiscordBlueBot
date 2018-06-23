package bluebot.commands.misc;

import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.time.format.DateTimeFormatter;

/**
 * @file WhoisCommand.java
 * @author Blue
 * @version 0.2
 * @brief Gives info about the mentioned user
 */
public class WhoisCommand implements Command {

    private final String HELP = "The command `whois` provides information about the mentioned user. \n\nUsage : `!whois @User`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args.length > 1) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(event.getMessage().getMentionedUsers().isEmpty()) {
            event.getTextChannel().sendMessage("No user mentioned.");
            return;
        }
        User user = event.getMessage().getMentionedUsers().get(0);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Information about " + user.getName() + "#" + user.getDiscriminator() , null, "http://i.imgur.com/880AyL6.png");
        builder.setColor(event.getGuild().getMemberById(user.getId()).getColor());
        builder.setThumbnail(user.getAvatarUrl());
        builder.addField(":id: User ID", user.getId(), true);

        String nickname = "None";
        if(event.getGuild().getMemberById(user.getId()).getNickname() != null) nickname = event.getGuild().getMemberById(user.getId()).getNickname();
        builder.addField(":information_source: Nickname", nickname, true);
        builder.addField(":computer: Status", event.getGuild().getMemberById(user.getId()).getOnlineStatus().name().toLowerCase(), true);

        String activity = "None";
        if(event.getGuild().getMemberById(user.getId()).getGame() != null) {
            activity = event.getGuild().getMemberById(user.getId()).getGame().getName();
        }
        builder.addField(":video_game: Activity", activity, true);

        String isOwner = "No";
        if( event.getGuild().getMemberById(user.getId()).isOwner()) {
            isOwner = "Yes";
        }
        builder.addField(":white_check_mark: Owner",  isOwner, true);

        String role = "No role";
        if(!event.getGuild().getMemberById(user.getId()).getRoles().isEmpty()) {
            role = event.getGuild().getMemberById(user.getId()).getRoles().get(0).getAsMention();
        }
        builder.addField(":medal: Higher role", role, true);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy h:mm a");
        builder.addField(":clock2: Creation date", user.getCreationTime().format(formatter), true);
        builder.addField(":inbox_tray:  Join date", event.getGuild().getMemberById(user.getId()).getJoinDate().format(formatter), true);

        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            event.getMessage().delete().queue();
        }
    }
}
