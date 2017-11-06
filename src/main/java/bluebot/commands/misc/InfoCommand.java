package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

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

        String message = new String();
        message += "**Users :**`" + usersNumber + "`\n";
        message += "**Servers :** `" + serversNumber + "`\n";
        message += "**Channels :** `" + channelNumber + "` text channels and `" + voiceChannelNumber +"` voice channels\n";
        message += "**Current game :** `" + game + "`\n";

        /*LocalDateTime curr = LocalDateTime.now();

        Duration dur = Duration.between(MainBot.getStartTime(), curr);
        long days = dur.toDays();
        long hours = dur.toHours();
        long minutes = dur.toMinutes();
        message += "Uptime : `" + days + "` day(s), `" + hours + "` hours and `" + minutes + "` minutes";*/

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

        message += "**Uptime :** " + uptime + "\n";

        event.getTextChannel().sendMessage(message).queue();
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
