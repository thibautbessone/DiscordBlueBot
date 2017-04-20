package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import sun.applet.Main;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;


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

        String game = MainBot.getJda().getSelfInfo().getCurrentGame().getName();

        String message = new String();
        message += "Users : `" + usersNumber + "`\n";
        message += "Servers : `" + serversNumber + "`\n";
        message += "Channels : `" + channelNumber + "` text channels and `" + voiceChannelNumber +"` voice channels\n";
        message += "Current game : `" + game + "`\n";

        /*LocalDateTime curr = LocalDateTime.now();

        Duration dur = Duration.between(MainBot.getStartTime(), curr);
        long days = dur.toDays();
        long hours = dur.toHours();
        long minutes = dur.toMinutes();
        message += "Uptime : `" + days + "` day(s), `" + hours + "` hours and `" + minutes + "` minutes";*/

        event.getTextChannel().sendMessage(message);
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            event.getTextChannel().sendMessage(help());
        }
    }
}
