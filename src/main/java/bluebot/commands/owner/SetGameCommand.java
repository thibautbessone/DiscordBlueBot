package bluebot.commands.owner;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @file SetGameCommand.java
 * @author Blue
 * @version 0.1
 * @brief Sets the current game of the bot
 */
public class SetGameCommand implements Command {

    private final String HELP = "The command `setgame` change the current bot activity / rich presence to the one given in parameter." +
                                "\nThe available types are `playing`, `listening` and `streaming`." +
                                "\nIf you are using the `streaming` type, provide a valid twitch URL after the activity (by default : mine)." +
                                "\nThis command requires to be the owner of the bot." +
                                "\n\nUsage (syntax is important) : `!setgame (type) [activity] streamLink`";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            return false;
        } else {
            if(event.getAuthor().getId().equals(MainBot.getBotOwner())) {
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
        String text = new String();
        String streamLink = new String("https://www.twitch.tv/bluretv");
        Pattern typeRegex = Pattern.compile("\\((.*?)\\)");
        Pattern activityRegex = Pattern.compile("\\[(.*?)]");

        String type;
        String activity;

        for(String arg : args) {
            text += arg + " ";
        }

        Matcher typeMatcher = typeRegex.matcher(text);
        Matcher activityMatcher = activityRegex.matcher(text);

        if (typeMatcher.find()) {
            type = typeMatcher.group(1);
        } else {
            event.getTextChannel().sendMessage("Please specify the type of the rich presence.").queue();
            return;
        }

        if (activityMatcher.find()) {
            activity = activityMatcher.group(1);
        } else {
            event.getTextChannel().sendMessage("Please specify the activity of the bot.").queue();
            return;
        }

        switch (type) {
            case "playing":
                MainBot.getJda().getPresence().setGame(Game.playing(activity));
                break;
            case "watching":
                MainBot.getJda().getPresence().setGame(Game.watching(activity));
                break;
            case "listening":
                MainBot.getJda().getPresence().setGame(Game.listening(activity));
                break;
            case "streaming":
                for(String arg : args) {
                    if (arg.contains("twitch.tv/")) streamLink = arg;
                }
                MainBot.getJda().getPresence().setGame(Game.streaming(activity, streamLink));
                break;
            default:
                event.getTextChannel().sendMessage("Wrong type specified.").queue();
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            if(!permissionFail) {
                event.getTextChannel().sendMessage(help()).queue();
            }
            permissionFail = false;
        }
    }
}
