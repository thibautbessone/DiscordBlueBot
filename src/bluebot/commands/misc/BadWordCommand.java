package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.ArrayList;

/**
 * @file BadWordCommand.java
 * @author Blue
 * @version 0.2
 * @brief Adds a badwords to the forbidden words list
 */
public class BadWordCommand implements Command {

    private final String HELP = "The command `bw` allow you to ban some words to be used on your server.\n\n" +
            "Usage : \n`!bw add yourWord` adds yourWord to the forbidden words list\n" +
            "`!bw rm yourWord` removes yourWord from the forbidden words list if it's in the list\n" +
            "`!bw list` sends you a DM with the forbidden words list.";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length == 0 || args[0].equals("help") || args.length > 2) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args[0].equals("list")) {
            String list = "```This is the list of the current forbidden words on the server : \n";
            list += "---------------------------------------------------------------\n\n";
            for(String word : MainBot.getBadWords().get(event.getGuild())) {
                list += word + "\n";
            }
            event.getTextChannel().sendMessage("Check your DMs.");
            event.getAuthor().getPrivateChannel().sendMessage(list + "```");
        } else if(args[0].equals("add")) {
            if(MainBot.getBadWords().containsKey(event.getGuild())) {
                MainBot.getBadWords().get(event.getGuild()).add(args[1]);
            } else {
                ArrayList<String> words = new ArrayList<>();
                words.add(args[0]);
                MainBot.getBadWords().put(event.getGuild(), words);
            }
            event.getTextChannel().sendMessage("The word has been added to the forbidden words list.");
        } else if(args[0].equals("rm")) {
            if(MainBot.getBadWords().get(event.getGuild()).contains(args[1])) {
                for(int i = 0; i < MainBot.getBadWords().get(event.getGuild()).size(); ++i) {
                    if(args[1].equals(MainBot.getBadWords().get(event.getGuild()).get(i))) {
                        MainBot.getBadWords().get(event.getGuild()).remove(i);
                        event.getTextChannel().sendMessage("The word `" + args[1] + "`has been removed from the forbidden words list");
                        return;
                    }
                }
            } else {
                event.getTextChannel().sendMessage("The specified word doesn't exist in the forbidden words list.");
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
            event.getTextChannel().sendMessage(help());
        }

    }
}
