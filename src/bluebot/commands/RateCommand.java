package bluebot.commands;

import bluebot.utils.Command;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.util.Random;

/**
 * Created by Thibaut on 11/01/2017.
 */
public class RateCommand implements Command {

    private final String HELP = "The command `rate` let you rate what you want. \n\nUsage : `!rate the thing you want to rate`";

    private final String responsePrefix = new String(":arrow_right: ");

    private String ten = new String("is incredibly awesome and should get the perfect score : ");
    private String eighttonine = new String("is (relatively) incredible and deserves a ");
    private String sixtoseven = new String("is quite good and should get a ");
    private String fourtofive = new String("is quite average and can be rated something like ");
    private String twotothree = new String("is bad and only deserves a ");
    private String zerotoone = new String("horribly sucks and gets what it deserves : a ");



    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(args.length == 0 || args[0].equals("help")) {
            event.getTextChannel().sendMessage(help());
            return;
        } else {
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
            //The thing to rate
            /*String text = new String("");
            for(String arg : args) {
                text += arg + " ";
            }*/
            event.getTextChannel().sendMessage(responsePrefix + event.getAuthorName() + ", I'd say that this " /*+ text */+ phrase + rating + "/10." + "\n\t\tThe truth has been told.");
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
