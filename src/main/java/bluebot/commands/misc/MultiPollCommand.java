package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @file MultiPollCommand.java
 * @author Blue
 * @version 0.1
 * @brief Creates a poll with multiple choices
 */
public class MultiPollCommand implements Command {

    private final String HELP = "The command `poll` creates a multiple choice (from 2 to 10 choices) poll.\n\nUsage (syntax is important) : `!poll (pollSubject) [Option1] [Option2] [Option3] ...`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            return false;
        } else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        String query = new String();
        String subject;
        String choicesList = new String();
        ArrayList<String> choices = new ArrayList<>();
        Pattern subjectRegex = Pattern.compile("\\((.*?)\\)");
        Pattern choiceRegex = Pattern.compile("\\[(.*?)]");

        for (String arg : args) {
            query += arg + " ";
        }

        Matcher subjectMatcher = subjectRegex.matcher(query);
        Matcher choiceMatcher = choiceRegex.matcher(query);

        while (choiceMatcher.find()) {
            choices.add(choiceMatcher.group(1));
        }

        if (subjectMatcher.find()) {
            subject = subjectMatcher.group(1);
        } else {
            event.getTextChannel().sendMessage("Please provide a poll question/subject.").queue();
            return;
        }

        if(choices.size() < 2 || choices.size() > 10) { //At least 2 options and max 10
            event.getTextChannel().sendMessage("You can only propose from 2 to 10 options.").queue();
            return;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
        builder.setAuthor(event.getAuthor().getName() + "#" + event.getAuthor().getDiscriminator() + " created a poll", null, event.getAuthor().getAvatarUrl());
        builder.addField("", "Question : " + "**" + subject + "**", true);

        for(int i = 0; i < choices.size(); ++i) {
            if(i == 9) {
                choicesList = choicesList + "\uD83D\uDD1F " + choices.get(i); //Keycap 10 case
                break;
            }
            choicesList = choicesList + (i+1) + "⃣ " + choices.get(i) + "\n"; //damn these emojis made me mad
        }
        builder.addField("", choicesList, false);
        builder.setFooter("Vote by adding a reaction below !", null);

        event.getMessage().delete().queue();
        event.getTextChannel().sendMessage(builder.build()).queue();

        //Now retrieves the last message from BlueBot to add reactions
        List<Message> history = event.getTextChannel().getHistory().retrievePast(10).complete(); //arbitrary ammount
        for (Message msg : history) {
            if(msg.getAuthor().getId().equals(MainBot.getJda().getSelfUser().getId())) {
                for(int i = 0; i < choices.size(); ++i) {
                    if(i == 9) {
                        msg.addReaction("\uD83D\uDD1F").queue(); //Keycap 10 case
                        break;
                    }
                    msg.addReaction(i+1 + "⃣").queue(); //damn these emojis made me mad
                }
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
