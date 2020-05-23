package bluebot.commands.fun;

import bluebot.utils.Command;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Random;

/**
 * @file RateCommand.java
 * @author Blue
 * @version 0.1
 * @brief Rates the string given in parameter
 */
public class RateCommand implements Command {

    private final String HELP = "The command `rate` let you rate what you want. \n\nUsage : `!rate the thing you want to rate`";

    private final String responsePrefix = ":arrow_right: ";

    private String ten = "is incredibly awesome and should get the perfect score : ";
    private String eighttonine = "is (relatively) incredible and deserves a ";
    private String sixtoseven = "is quite good and should get a ";
    private String fourtofive = "is quite average and can be rated something like ";
    private String twotothree = "is bad and only deserves a ";
    private String zerotoone = "horribly sucks and gets what it deserves : a ";



    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        //The phrase to say
        String phrase;
         //The random rating
        Random rand = new Random();
        int rating = rand.nextInt(10 + 1);
        if(rating < 2) {
            phrase = zerotoone;
        }
        else if(rating >= 2 && rating < 4) {
            phrase = twotothree;
        }
        else if(rating >= 4 && rating < 6) {
            phrase = fourtofive;
        }
        else if(rating >= 6 && rating < 8) {
            phrase = sixtoseven;
        }
        else if(rating >= 8 && rating < 10) {
            phrase = eighttonine;
        }
        else phrase = ten;
        event.getTextChannel().sendMessage(responsePrefix + event.getAuthor().getName() + ", I'd say that this " /*+ text */+ phrase + rating + "/10.").queue();
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
