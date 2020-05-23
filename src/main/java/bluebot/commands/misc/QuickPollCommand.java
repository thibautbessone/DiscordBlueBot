package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.List;

/**
 * @file QuickPollCommand.java
 * @author Blue
 * @version 0.1
 * @brief Creates a poll with user question
 */
public class QuickPollCommand implements Command {

    private final String HELP = "The command `qpoll` creates a poll with simple vote options.\n\nUsage : `!qpoll the poll question/subject`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            return false;
        } else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String query = "";
        for (String arg : args) {
            query += arg + " ";
        }
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
        builder.setAuthor(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " created a poll", null, event.getAuthor().getAvatarUrl());
        builder.addField("", ":grey_question: Question : " + "**" + query + "**", false);
        builder.setFooter("Vote by adding a reaction below !", null);

        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(builder.build()).queue();

        //Now retrieves the last message from BlueBot to add reactions
        List<Message> history = event.getTextChannel().getHistory().retrievePast(10).complete(); //arbitrary ammount
        for (Message msg : history) {
            if(msg.getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
                msg.addReaction("\uD83D\uDC4D").queue();
                msg.addReaction("\ud83d\udc4e").queue();
                msg.addReaction("\uD83E\uDD37").queue();
                break;
            }
        }
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
