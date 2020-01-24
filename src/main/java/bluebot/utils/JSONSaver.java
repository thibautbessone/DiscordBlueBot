package bluebot.utils;

import bluebot.MainBot;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @file JSONSaver.java
 * @author Blue
 * @version 0.1
 * @brief Loads all JSON files containing settings for each server
 */
public class JSONSaver {

    private static Logger logger = Logger.getLogger(JSONSaver.class);

    private Gson gsonTool = new Gson();

    private String streamerList;
    private String autoRoleList;
    private String selfAssignedRolesList;

    private String badWords;
    private String prefixes;

    private String twitchDisabled;
    private String cleverBotDisabled;
    private String bwDisabled;
    private String userEventDisabled;
    private String serverSBDisabled;

    private String twitchChannel;
    private String userEventChannel;
    private String musicChannel;

    private String userEventsMessages;

    public JSONSaver() {
        streamerList = gsonTool.toJson(MainBot.getStreamerList());
        autoRoleList = gsonTool.toJson(MainBot.getAutoRoleList());
        selfAssignedRolesList = gsonTool.toJson(MainBot.getSelfAssignedRolesList());

        badWords = gsonTool.toJson(MainBot.getBadWords());
        prefixes = gsonTool.toJson(MainBot.getPrefixes());

        twitchDisabled = gsonTool.toJson(MainBot.getTwitchDisabled());
        cleverBotDisabled = gsonTool.toJson(MainBot.getCleverBotDisabled());
        bwDisabled = gsonTool.toJson(MainBot.getBwDisabled());
        userEventDisabled = gsonTool.toJson(MainBot.getUserEventDisabled());
        serverSBDisabled = gsonTool.toJson(MainBot.getServerSBDisabled());

        twitchChannel = gsonTool.toJson(MainBot.getTwitchChannel());
        userEventChannel = gsonTool.toJson(MainBot.getUserEventChannel());
        musicChannel = gsonTool.toJson(MainBot.getMusicChannel());

        userEventsMessages = gsonTool.toJson(MainBot.getUserEventsMessages());

        try {
            //Opening properties file
            File configFile = new File("config.blue");
            FileInputStream fileInput = new FileInputStream(configFile);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            FileOutputStream fileOutput = new FileOutputStream(configFile);
            properties.setProperty("streamerList", streamerList);
            properties.setProperty("autoRoleList", autoRoleList);
            properties.setProperty("selfAssignedRolesList", selfAssignedRolesList);

            properties.setProperty("badWords", badWords);
            properties.setProperty("prefixes", prefixes);

            properties.setProperty("twitchDisabled", twitchDisabled);
            properties.setProperty("cleverBotDisabled", cleverBotDisabled);
            properties.setProperty("bwDisabled", bwDisabled);
            properties.setProperty("userEventDisabled", userEventDisabled);
            properties.setProperty("serverSBDisabled", serverSBDisabled);

            properties.setProperty("twitchChannel", twitchChannel);
            properties.setProperty("userEventChannel", userEventChannel);
            properties.setProperty("musicChannel", musicChannel);

            properties.setProperty("userEventsMessages", userEventsMessages);

            //Storing config
            properties.store(fileOutput, null);
            logger.info("Properties saved");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
