package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import bluebot.utils.listeners.TwitchListener;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.List;

/**
 * Created by Thibaut on 17/02/2017.
 */
public class TrackTwitchCommand implements Command {

    private final String HELP = "The command `tracktwitch` makes the bot when the specified user is streaming \n\nUsage : `!tracktwitch @TrackedUser streamLink`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args.length > 2 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        }
        else {
            if (event.getMessage().getMentionedUsers().isEmpty() || event.getMessage().getMentionedUsers().size() > 1)
            {
                event.getTextChannel().sendMessage("No user or too many users mentioned.");
            }
            else {
                MainBot.getStreamerList().put(event.getMessage().getMentionedUsers().get(0).getId(), args[1]);
                event.getTextChannel().sendMessage(event.getMessage().getMentionedUsers().get(0).getUsername() + " added to the tracked streamers");
            }

        }

    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
