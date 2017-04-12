package bluebot;

import bluebot.commands.fun.*;
import bluebot.commands.fun.quickreactions.IDGFCommand;
import bluebot.commands.fun.quickreactions.KappaCommand;
import bluebot.commands.fun.quickreactions.NopeCommand;
import bluebot.commands.fun.quickreactions.WatCommand;
import bluebot.commands.misc.*;
import bluebot.commands.moderation.SetAutoRoleCommand;
import bluebot.commands.moderation.SetPrefixCommand;
import bluebot.commands.utility.*;
import bluebot.utils.*;
import bluebot.utils.listeners.*;
import net.dv8tion.jda.*;
import net.dv8tion.jda.entities.Guild;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private static LocalDateTime startTime = LocalDateTime.now();
    public static final CommandParser parser = new CommandParser();
    public static TreeMap<String, Command> commands = new TreeMap<String, Command>();
    private static Map<String, String> streamerList =  new HashMap<>();
    private static Map<String, String> autoRoleList = new HashMap<>();
    private static Map<Guild, ArrayList<String>> badWords = new HashMap<>();
    private static Map<Guild, String> prefixes = new HashMap<>();

    public static Map<Guild, String> getPrefixes() {
        return prefixes;
    }

    private static String basePrefix = "!";

    /*public static void setBasePrefix(String basePrefix) {
        MainBot.basePrefix = basePrefix;
    }*/

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
            LoadingProperties config = new LoadingProperties();
            jda = new JDABuilder().setBotToken(config.getBotToken())
                    .addListener(new CleverbotListener())
                    .addListener(new TwitchListener())
                    .addListener(new MessageReceivedListener())
                    .addListener(new BadWordsListener())
                    .addListener(new UserJoinLeaveListener()).setBulkDeleteSplittingEnabled(false).buildBlocking();

            jda.getAccountManager().setGame(config.getBotActivity());
            System.out.println("Current activity " + jda.getSelfInfo().getCurrentGame());
            System.out.println("Connected servers : " + jda.getGuilds().size());
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
        commands.put("setprefix", new SetPrefixCommand());
        commands.put("sound", new PlaySoundCommand());
        commands.put("tracktwitch", new TrackTwitchCommand());
        commands.put("setautorole", new SetAutoRoleCommand());
        commands.put("cat", new CatCommand());
        commands.put("idgf", new IDGFCommand());
        commands.put("bw", new BadWordCommand());
        commands.put("info", new InfoCommand());
        commands.put("steam", new SteamStatusCommand());
        commands.put("kappa", new KappaCommand());
        //commands.put("prune", new PruneCommand());

    }

    public static JDA getJda() {
        return jda;
    }
    public static Map<String, String> getStreamerList() {return streamerList;}
    public static Map<String, String> getAutoRoleList() {return autoRoleList;}
    public static LocalDateTime getStartTime() {return startTime;}
    public static Map<Guild, ArrayList<String>> getBadWords() {return badWords;}
    public static String getBasePrefix() {return basePrefix;}


}