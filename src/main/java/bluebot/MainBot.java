package bluebot;

import bluebot.commands.fun.*;
import bluebot.commands.fun.quickreactions.IDGFCommand;
import bluebot.commands.fun.quickreactions.KappaCommand;
import bluebot.commands.fun.quickreactions.NopeCommand;
import bluebot.commands.fun.quickreactions.WatCommand;
import bluebot.commands.misc.*;
import bluebot.commands.moderation.*;
import bluebot.commands.owner.AnnouncementCommand;
import bluebot.commands.owner.SetGameCommand;
import bluebot.commands.owner.SetOnlineStateCommand;
import bluebot.commands.owner.ShutDownCommand;
import bluebot.commands.utility.*;
import bluebot.utils.Command;
import bluebot.utils.CommandParser;
import bluebot.utils.LoadingProperties;
import bluebot.utils.SaveThread;
import bluebot.utils.listeners.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @file MainBot.java
 * @author Blue
 * @version 1
 * @brief The main class of BlueBot
 */

public class MainBot {

    private static boolean isPublicInstance = false;
    private static JDA jda;
    private static LoadingProperties config = new LoadingProperties();
    private static AudioPlayerManager playerManager;


    private static String botOwner;
    public static final CommandParser parser = new CommandParser();
    //public static  Map<String, Command> commands = new TreeMap<String, Command>();
    public static  Map<String, Command> funCommands = new TreeMap<String, Command>();
    public static  Map<String, Command> modUtilCommands = new TreeMap<String, Command>();
    public static  Map<String, Command> miscCommands = new TreeMap<String, Command>();
    public static  Map<String, Command> ownerCommands = new TreeMap<String, Command>();
    private static Map<String, String> streamerList =  new HashMap<>();
    private static Map<String, String> autoRoleList = new HashMap<>();

    private static Map<String, ArrayList<String>> badWords = new HashMap<>();
    private static Map<String, String> prefixes = new HashMap<>();
    private static Map<String, String> bannedServers = new HashMap<>(); //server, reason for ban

    private static ArrayList<String> twitchDisabled = new ArrayList<>();
    private static ArrayList<String> cleverBotDisabled = new ArrayList<>();
    private static ArrayList<String> bwDisabled = new ArrayList<>();
    private static ArrayList<String> userEventDisabled = new ArrayList<>();
    private static ArrayList<String> serverSBDisabled = new ArrayList<>();
    private static ArrayList<String> nameProtectionDisabled = new ArrayList<>();


    private static Map<String, String> twitchChannel = new HashMap<>();
    private static Map<String, String> userEventChannel = new HashMap<>();
    private static Map<String, String> musicChannel = new HashMap<>();

    public static Map<String, String> getBannedServers() {
        return bannedServers;
    }
    public static String getBotOwner() {
        return botOwner;
    }
    public static ArrayList<String> getTwitchDisabled() {
        return twitchDisabled;
    }
    public static ArrayList<String> getCleverBotDisabled() {
        return cleverBotDisabled;
    }
    public static ArrayList<String> getBwDisabled() {
        return bwDisabled;
    }
    public static ArrayList<String> getUserEventDisabled() {
        return userEventDisabled;
    }
    public static Map<String, String> getTwitchChannel() {
        return twitchChannel;
    }
    public static Map<String, String> getUserEventChannel() {
        return userEventChannel;
    }
    public static Map<String, String> getMusicChannel() {
        return musicChannel;
    }
    public static ArrayList<String> getServerSBDisabled() {
        return serverSBDisabled;
    }
    public static boolean isPublicInstance() {
        return isPublicInstance;
    }
    public static void setPublicInstance(boolean publicInstance) {
        isPublicInstance = publicInstance;
    }
        private static String basePrefix = "!";

    public static void handleCommand(CommandParser.CommandContainer cmdContainer) {
        Map<String, Command> localMap = new TreeMap<String, Command>();
        if(funCommands.containsKey(cmdContainer.invoke)) localMap = funCommands;
        if(modUtilCommands.containsKey(cmdContainer.invoke)) localMap = modUtilCommands;
        if(miscCommands.containsKey(cmdContainer.invoke)) localMap = miscCommands;
        if(ownerCommands.containsKey(cmdContainer.invoke)) localMap = ownerCommands;

        if(!localMap.isEmpty()) {
            boolean safe = localMap.get(cmdContainer.invoke).called(cmdContainer.args, cmdContainer.event);
            if(safe) {
                localMap.get(cmdContainer.invoke).action(cmdContainer.args, cmdContainer.event);
                localMap.get(cmdContainer.invoke).executed(safe, cmdContainer.event);
            }
            else {
                localMap.get(cmdContainer.invoke).executed(safe, cmdContainer.event);
            }
        }
    }


    public MainBot() {
        try {
            //jda instanciation
            //default method as provided in the API
            config = new LoadingProperties();
            SaveThread saveThread = new SaveThread();

            playerManager = new DefaultAudioPlayerManager();
            AudioSourceManagers.registerLocalSource(playerManager);

            jda = new JDABuilder(AccountType.BOT).setToken(config.getBotToken())
                    .addEventListener(new TwitchListener())
                    //.addListener(new CleverbotListener())
                    .addEventListener(new BadWordsListener())
                    .addEventListener(new UserJoinLeaveListener())
                    .addEventListener(new BotKickedListener())
                    .addEventListener(new BannedServersListener())
                    .addEventListener(new MessageReceivedListener())
                    //.addEventListener(new VCJoinedListener())
                    .setBulkDeleteSplittingEnabled(false).buildBlocking();

            botOwner = config.getBotOwner();
            jda.getPresence().setGame(Game.of(config.getBotActivity()));
            System.out.println("Current activity " + jda.getPresence().getGame());

            //Loading the previous state of the bot(before shutdown)
            Gson gson = new Gson();
            streamerList = gson.fromJson(config.getStreamerList(), new TypeToken<Map<String, String>>(){}.getType());
            autoRoleList = gson.fromJson(config.getAutoRoleList(), new TypeToken<Map<String, String>>(){}.getType());

            badWords = gson.fromJson(config.getBadWords(), new TypeToken<Map<String, ArrayList<String>>>(){}.getType());
            prefixes = gson.fromJson(config.getPrefixes(), new TypeToken<Map<String, String>>(){}.getType());

            twitchDisabled = gson.fromJson(config.getTwitchDisabled(), new TypeToken<ArrayList<String>>(){}.getType());
            cleverBotDisabled = gson.fromJson(config.getCleverBotDisabled(), new TypeToken<ArrayList<String>>(){}.getType());
            bwDisabled = gson.fromJson(config.getBwDisabled(), new TypeToken<ArrayList<String>>(){}.getType());
            userEventDisabled = gson.fromJson(config.getUserEventDisabled(), new TypeToken<ArrayList<String>>(){}.getType());

            twitchChannel = gson.fromJson(config.getTwitchChannel(), new TypeToken<Map<String, String>>(){}.getType());
            userEventChannel = gson.fromJson(config.getUserEventChannel(), new TypeToken<Map<String, String>>(){}.getType());
            musicChannel = gson.fromJson(config.getMusicChannel(), new TypeToken<Map<String, String>>(){}.getType());

            saveThread.run();

            System.out.println("Connected servers : " + jda.getGuilds().size());
            System.out.println("Concerned users : " + jda.getUsers().size());

        } catch (InterruptedException | LoginException | RateLimitedException e) {
         System.out.println("No internet connection or invalid or missing token. Please edit config.blue and try again.");
        }

        //Banned servers
        bannedServers.put("304031909648793602", "spamming w/ bots to crash small bots");
        //bannedServers.put("281978005088370688", "BlueBot TestServer");

        //Activated bot commands

        //Fun commands
        funCommands.put("idgf", new IDGFCommand());
        funCommands.put("kappa", new KappaCommand());
        funCommands.put("nope", new NopeCommand());
        funCommands.put("wat", new WatCommand());
        funCommands.put("cat", new CatCommand());
        funCommands.put("c&h", new CyanideHapinessCommand());
        funCommands.put("dog", new DogCommand());
        funCommands.put("gif", new GifCommand());
        funCommands.put("rate", new RateCommand());
        funCommands.put("xkcd", new XKCDCommand());
        funCommands.put("ymjoke", new YoMommaJokeCommand());

        //Miscellaneous commands
        miscCommands.put("call", new CallCommand());
        miscCommands.put("github", new GitHubCommand());
        miscCommands.put("info", new InfoCommand());
        miscCommands.put("invite", new InviteCommand());
        miscCommands.put("poll", new MultiPollCommand());
        miscCommands.put("sound", new PlaySoundCommand());
        miscCommands.put("qpoll", new QuickPollCommand());
        miscCommands.put("rmsound", new RemoveSoundCommand());
        miscCommands.put("server", new ServerCommand());
        miscCommands.put("steam", new SteamStatusCommand());
        miscCommands.put("tracktwitch", new TrackTwitchCommand());
        miscCommands.put("untrack", new UntrackCommand());
        miscCommands.put("whois", new WhoisCommand());

        //Moderation & utility commands
        modUtilCommands.put("bw", new BadWordCommand());
        modUtilCommands.put("enable", new EnableListenerCommand());
        modUtilCommands.put("disable", new DisableListenerCommand());
        modUtilCommands.put("setautorole", new SetAutoRoleCommand());
        modUtilCommands.put("setprefix", new SetPrefixCommand());
        modUtilCommands.put("channel", new SpecificChannelCommand());

        modUtilCommands.put("clear", new ClearCommand());
        modUtilCommands.put("help", new HelpCommand());
        modUtilCommands.put("ping", new PingCommand());
        modUtilCommands.put("say", new SayCommand());
        modUtilCommands.put("sayhi", new SayHiCommand());
        modUtilCommands.put("whoareyou", new WhoAreYouCommand());

        //Owner commands
        ownerCommands.put("announce", new AnnouncementCommand());
        ownerCommands.put("setgame", new SetGameCommand());
        ownerCommands.put("setos", new SetOnlineStateCommand());
        ownerCommands.put("shutdown", new ShutDownCommand());

        //commands.put("prune", new PruneCommand());
    }

    public static JDA getJda() {return jda;}
    public static LoadingProperties getConfig() {
        return config;
    }
    public static AudioPlayerManager getPlayerManager() {return playerManager;}
    public static Map<String, String> getStreamerList() {return streamerList;}
    public static Map<String, String> getAutoRoleList() {return autoRoleList;}
    public static Map<String, ArrayList<String>> getBadWords() {return badWords;}
    public static String getBasePrefix() {return basePrefix;}
    public static Map<String, String> getPrefixes() {return prefixes;}
    public static ArrayList<String> getNameProtectionDisabled() {return nameProtectionDisabled;}
}