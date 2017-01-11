package bluebot;

import bluebot.commands.PingCommand;
import bluebot.commands.SayHiCommand;
import bluebot.utils.CommandParser;
import net.dv8tion.jda.*;
import net.dv8tion.jda.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

/**
 * @file MainBot.java
 * @author Blue
 * @version 0.1
 * @brief The main class of BlueBot
 */

public class MainBot {

    private JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static HashMap<String, Command> commands = new HashMap<String, Command>();

    public static void handleCommand(CommandParser.CommandContainer cmdContainer) {
        if(commands.containsKey(cmdContainer.invoke)) {
            boolean safe = commands.get(cmdContainer.invoke).called(cmdContainer.args, cmdContainer.event);
            if(safe) {
                commands.get(cmdContainer.invoke).action(cmdContainer.args, cmdContainer.event);
                commands.get(cmdContainer.invoke).executed(safe, cmdContainer.event);
            }
            else {
                commands.get(cmdContainer.invoke).executed(safe, cmdContainer.event);
            }
        }
    }

    public MainBot(String token) {
        try {
            //jda instanciation
            //default method as provided in the API
            jda = new JDABuilder().setBotToken(token).addListener(new BotListener()).setBulkDeleteSplittingEnabled(false).buildBlocking();

        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error, check your internet connection");
            return;
        }

        //Activated bot commands
        commands.put("ping", new PingCommand());
        commands.put("sayhi", new SayHiCommand());

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Missing bot token");
        }
        new MainBot(args[0]);

    }

}