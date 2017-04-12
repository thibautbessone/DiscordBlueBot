package bluebot.commands.utility;

import bluebot.utils.Command;
import net.dv8tion.jda.MessageHistory;
import net.dv8tion.jda.entities.Message;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibaut on 18/03/2017.
 */
public class PruneCommand implements Command {

    private final String HELP = "The command `prune` deletes the number of messages given in parameter. \n\nUsage : `!prune number (over 1)`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        //if(args.length == 0 || args[0].equals("help")) {return false;}
        //else return true;
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        int nbMsg = 99;
        try {
            OffsetDateTime testDate = OffsetDateTime.parse(args[0] + "-" + args[1] + "-" + args[2] + "T12:00:00+01:00");
            event.getTextChannel().sendMessage(testDate.toString()); //TEST

            MessageHistory history = event.getTextChannel().getHistory();
            System.out.println(history);
            List<Message> test = history.retrieve();
            event.getTextChannel().sendMessage("Total : " + test.size());
            ArrayList<Message> toDelete = new ArrayList<>();

            for(Message mes : test) {
                if(mes.getTime().isBefore(testDate)) {
                    toDelete.add(mes);
                }
            }

            for (int i = 0; i < toDelete.size(); i += nbMsg) {
                List<Message> sub = toDelete.subList(i, Math.min(toDelete.size(),i+nbMsg));
                if(sub.size() == 1) {
                    sub.get(0).deleteMessage();
                }
                else if(sub.size() < nbMsg) {
                    event.getTextChannel().deleteMessages(sub);
                    break; // no more messages
                }
                else {
                    event.getTextChannel().deleteMessages(sub);
                    Thread.sleep(2000);
                }
            }
            event.getTextChannel().sendMessage("Messages deleted : `" + toDelete.size() + "`");


        } catch (NumberFormatException e) {
            event.getTextChannel().sendMessage("Invalid entry. Please type a number.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if (!success) {
            event.getTextChannel().sendMessage(help());
        }
    }
}
