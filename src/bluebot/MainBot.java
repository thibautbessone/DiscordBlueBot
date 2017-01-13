package bluebot;

import bluebot.commands.*;
import bluebot.utils.*;
import net.dv8tion.jda.*;

import javax.security.auth.login.LoginException;
import java.util.HashMap;

/**
 * @file MainBot.java
 * @author Blue
 * @version 0.2
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

    /**
     * @brief Creates the conneciton with the server
     * @param token
     *      the token of the bot
     */
    public MainBot(String token) {
        try {
            //jda instanciation
            //default method as provided in the API
            jda = new JDABuilder().setBotToken(token).addListener(new BotListener()).setBulkDeleteSplittingEnabled(false).buildBlocking();
            System.out.println("Connected servers : " + jda.getGuilds().size());
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
            System.out.println("Error, check your internet connection");
            return;
        }

        //Activated bot commands
        commands.put("ping", new PingCommand());
        commands.put("sayhi", new SayHiCommand());
        commands.put("say", new SayCommand());
        commands.put("rate", new RateCommand());
        commands.put("clear", new ClearCommand());
        commands.put("whoareyou", new WhoAreYouCommand());

    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Missing bot token");
        }
        new MainBot(args[0]);
    }

}