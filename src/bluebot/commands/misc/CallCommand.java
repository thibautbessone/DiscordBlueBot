package bluebot.commands.misc;

import bluebot.utils.Command;
import net.dv8tion.jda.entities.User;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

/**
 * @file CallCommand.java
 * @author Blue
 * @version 0.1
 * @brief Allow users to call each others using appear.in website
 */
public class CallCommand implements Command {

    private final String HELP = "The command `call` allows you to video call the mentioned user(s) \n\nUsage : `!call @User1 @User2`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if (event.getMessage().getMentionedUsers().isEmpty())
        {
            event.getTextChannel().sendMessage("No user mentioned.");
        }
        else {
            String url = "https://appear.in/bluebot-";
            for (int i = 0; i < 10; ++i) {
                int rand = (int)(Math.random()*10);
                url += rand;
            }
            //Send DMs to concerned users
            event.getAuthor().getPrivateChannel().sendMessage("Go to " + url);
            User lastUser = null;
            for(User user : event.getMessage().getMentionedUsers()) {
                //To prevent DM spam from the bot in mentioned multiple times
                if(user != event.getAuthor() && user != lastUser) {
                    user.getPrivateChannel().sendMessage("Go to " + url);
                    lastUser = user;
                }
            }
            event.getTextChannel().sendMessage("Mentioned user(s), check your DMs.");
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
