package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.lang.management.ManagementFactory;


/**
 * @file InfoCommand.java
 * @author Blue
 * @version 0.2
 * @brief Provides information on the bot
 */
public class InfoCommand implements Command {

    private final String HELP = "The command `info` provides information on the bot.\n\nUsage : `!info`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        int usersNumber = MainBot.getJda().getUsers().size();
        int serversNumber = MainBot.getJda().getGuilds().size();
        int channelNumber = MainBot.getJda().getTextChannels().size();
        int voiceChannelNumber = MainBot.getJda().getVoiceChannels().size();

        String game = MainBot.getJda().getPresence().getGame().getName();

        //Taken from Almighty Alpaca
        //https://github.com/Java-Discord-Bot-System/Plugin-Uptime/blob/master/src/main/java/com/almightyalpaca/discord/bot/plugin/uptime/UptimePlugin.java#L28-L42
        final long duration = ManagementFactory.getRuntimeMXBean().getUptime();

        final long years = duration / 31104000000L;
        final long months = duration / 2592000000L % 12;
        final long days = duration / 86400000L % 30;
        final long hours = duration / 3600000L % 24;
        final long minutes = duration / 60000L % 60;
        final long seconds = duration / 1000L % 60;
        // final long milliseconds = duration % 1000;

        String uptime = (years == 0 ? "" : "`" + years + "` Years, ") + (months == 0 ? "" : "`" + months + "` Months, ") + (days == 0 ? "" : "`" + days + "` Days, ") + (hours == 0 ? "" : "`" + hours + "` Hours, ")
                + (minutes == 0 ? "" : "`" + minutes + "` Minutes, ") + (seconds == 0 ? "" : "`" + seconds + "` Seconds, ") /* + (milliseconds == 0 ? "" : milliseconds + " Milliseconds, ") */;

        uptime = replaceLast(uptime, ", ", "");
        uptime = replaceLast(uptime, ",", " and");




        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("Information about " + MainBot.getJda().getSelfUser().getName(), "https://bluebot.pw", "http://i.imgur.com/880AyL6.png");
        builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
        builder.setThumbnail(MainBot.getJda().getSelfUser().getAvatarUrl());


        builder.addField("Owner :white_check_mark:", MainBot.getJda().getUserById(MainBot.getConfig().getBotOwner()).getName() + "#" + MainBot.getJda().getUserById(MainBot.getConfig().getBotOwner()).getDiscriminator(), true);
        builder.addBlankField(true);

        builder.addField("Users :busts_in_silhouette:", String.valueOf(usersNumber), true);
        builder.addField("Servers :desktop:", String.valueOf(serversNumber), true);
        builder.addField("Channels :keyboard: :loud_sound:", String.valueOf(channelNumber) + " text / " + String.valueOf(voiceChannelNumber) + " voice channels", true);
        builder.addBlankField(true);

        builder.addField("Current activity :video_game:", game, true);
        builder.addField("Uptime :timer:", uptime, true);

        event.getTextChannel().sendMessage(builder.build()).queue();
    }

    //Taken from Almighty Alpaca
    //https://github.com/Java-Discord-Bot-System/Core/blob/master/src/main/java/com/almightyalpaca/discord/bot/system/util/StringUtils.java#L15-L17
    private String replaceLast(final String text, final String regex, final String replacement) {
        return text.replaceFirst("(?s)(.*)" + regex, "$1" + replacement);
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            event.getTextChannel().sendMessage(help()).queue();
        }
    }
}
