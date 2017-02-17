package bluebot;

import bluebot.commands.fun.*;
import bluebot.commands.fun.quickreactions.NopeCommand;
import bluebot.commands.fun.quickreactions.WatCommand;
import bluebot.commands.misc.TrackTwitchCommand;
import bluebot.commands.utility.*;
import bluebot.utils.*;
import bluebot.utils.listeners.MessageReceivedListener;
import bluebot.utils.listeners.TwitchListener;
import net.dv8tion.jda.*;
import net.dv8tion.jda.entities.Guild;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @file MainBot.java
 * @author Blue
 * @version 0.2
 * @brief The main class of BlueBot
 */

public class MainBot {


    private static JDA jda;
    public static final CommandParser parser = new CommandParser();
    public static TreeMap<String, Command> commands = new TreeMap<String, Command>();
    private static Map<String, String> streamerList =  new HashMap<>();
    public static String prefix;


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


    public MainBot() {
        try {
            //jda instanciation
            //default method as provided in the API
            prefix = "!"; //default prefix
            LoadingProperties config = new LoadingProperties();
            jda = new JDABuilder().setBotToken(config.getBotToken()).addListener(new TwitchListener()).addListener(new MessageReceivedListener()).setBulkDeleteSplittingEnabled(false).buildBlocking();
            jda.getAccountManager().setGame(config.getBotActivity());
            System.out.println("Current activity " + jda.getSelfInfo().getCurrentGame());
            System.out.println("Connected servers : " + jda.getGuilds());
            System.out.println("Concerned users : " + jda.getUsers().size());
        } catch (InterruptedException e) {
            System.out.println("Error, check your internet connection");
            return;
        } catch (LoginException e) {
            System.out.println("Invalid or missing token. Please edit config.blue and try again.");
            return;
        }



        //Activated bot commands
        commands.put("ping", new PingCommand());
        commands.put("sayhi", new SayHiCommand());
        commands.put("say", new SayCommand());
        commands.put("rate", new RateCommand());
        commands.put("clear", new ClearCommand());
        commands.put("whoareyou", new WhoAreYouCommand());
        commands.put("help", new HelpCommand());
        commands.put("nope", new NopeCommand());
        commands.put("ymjoke", new YoMommaJokeCommand());
        commands.put("wat", new WatCommand());
        commands.put("gif", new GifCommand());
        commands.put("c&h", new CyanideHapinessCommand());
        //commands.put("steam", new SteamUserInfoCommand());
        //commands.put("setprefix", new SetPrefixCommand());
        commands.put("tracktwitch", new TrackTwitchCommand());

    }

    public static JDA getJda() {
        return jda;
    }
    public static Map<String, String> getStreamerList() {return streamerList;}


}