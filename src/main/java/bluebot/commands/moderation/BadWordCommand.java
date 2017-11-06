package bluebot.commands.moderation;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.utils.PermissionUtil;

import java.util.ArrayList;

/**
 * @file BadWordCommand.java
 * @author Blue
 * @version 0.2
 * @brief Adds a badwords to the forbidden words list
 */
public class BadWordCommand implements Command {

    private final String HELP = "The command `bw` allow you to ban some words to be used on your server." +
            "\nThis command requires the manage messages permission." +
            "\n\nUsage : \n`!bw add yourWord` adds yourWord to the forbidden words list\n" +
            "`!bw rm yourWord` removes yourWord from the forbidden words list if it's in the list\n" +
            "`!bw list` sends you a DM with the forbidden words list.";
    private boolean permissionFail = false;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if (args.length == 0 || args[0].equals("help") || args.length > 2) {return false;}
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
        if(args[0].equals("list")) {
            String list = "```This is the list of the current forbidden words on the server : \n";
            list += "---------------------------------------------------------------\n\n";
            for(String word : MainBot.getBadWords().get(event.getGuild().getId())) {
                list += word + "\n";
            }
            event.getTextChannel().sendMessage("Check your DMs.").queue();
            //event.getAuthor().getPrivateChannel().sendMessage(list + "```");
            String finalList = list; //lambda expression
            event.getAuthor().openPrivateChannel().queue( (channel) -> channel.sendMessage(finalList + "```").queue() );

        } else if(args[0].equals("add")) {
            if(MainBot.getBadWords().containsKey(event.getGuild().getId())) {
                MainBot.getBadWords().get(event.getGuild().getId()).add(args[1]);
            } else {
                ArrayList<String> words = new ArrayList<>();
                words.add(args[0]);
                MainBot.getBadWords().put(event.getGuild().getId(), words);
            }
            event.getTextChannel().sendMessage("The word has been added to the forbidden words list.").queue();
        } else if(args[0].equals("rm")) {
            if(MainBot.getBadWords().get(event.getGuild().getId()).contains(args[1])) {
                for(int i = 0; i < MainBot.getBadWords().get(event.getGuild().getId()).size(); ++i) {
                    if(args[1].equals(MainBot.getBadWords().get(event.getGuild().getId()).get(i))) {
                        MainBot.getBadWords().get(event.getGuild().getId()).remove(i);
                        event.getTextChannel().sendMessage("The word `" + args[1] + "`has been removed from the forbidden words list").queue();
                        return;
                    }
                }
            } else {
                event.getTextChannel().sendMessage("The specified word doesn't exist in the forbidden words list.").queue();
            }
        }
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
