package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @file CustomEmbedCommand.java
 * @author Blue
 * @version 0.1
 * @brief Creates a embed with the specified query //TEST DEV
 */
public class CustomEmbedCommand implements Command {

    private final String HELP = "The command `embed` allows you to post an embed message." +
            "\nThis command requires the manage messages permissions." +
            "\n\nUsage (syntax is important) : `!embed (message)`";

    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else {
            if(PermissionUtil.checkPermission(event.getTextChannel(), event.getMember(), Permission.MESSAGE_MANAGE)) {
                return true;
            } else {
                event.getTextChannel().sendMessage(event.getAuthor().getAsMention() + ", you don't have the permission to do that.").queue();
                permissionFail = true;
                return false;
            }
        }
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String query = new String();
        Pattern subjectRegex = Pattern.compile("\\((.*?)\\)");
        String subject;

        for (String arg : args) {
            query += arg + " ";
        }

        Matcher subjectMatcher = subjectRegex.matcher(query);

        if (subjectMatcher.find()) {
            subject = subjectMatcher.group(1);
        } else {
            event.getTextChannel().sendMessage("Please provide a message").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
        builder.setAuthor(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " : " + subject, null, event.getAuthor().getAvatarUrl());
        builder.addField("","**" + subject + "**", false);


        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if (!success) {
            if(!permissionFail) {
                event.getTextChannel().sendMessage(help()).queue();
            }
            permissionFail = false;
        }
    }
}
