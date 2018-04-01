package bluebot.utils;

import com.google.gson.Gson;

import java.io.*;
import java.util.Properties;

/**
 * @file LoadingProperties.java
 * @author Blue
 * @version 0.1
 * @brief Loads a config file to launch the bot.
 */
public class LoadingProperties {

    private Gson gsonTool = new Gson();

    private String botToken;
    private String botActivity;
    private String steamAPIKey;
    private String cleverbotAPIKey;
    private String botOwner;

    private String streamerList;
    private String autoRoleList;

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


    private String embedColor;

    public String getCleverbotAPIKey() {
        return cleverbotAPIKey;
    }
    public String getBotToken() {
        return botToken;
    }
    public String getSteamAPIKey() {return steamAPIKey;}
    public String getBotActivity() {
        return botActivity;
    }
    public String getBotOwner() {
        return botOwner;
    }
    public String getStreamerList() {
        return streamerList;
    }
    public String getAutoRoleList() {
        return autoRoleList;
    }
    public String getBadWords() {
        return badWords;
    }
    public String getPrefixes() {
        return prefixes;
    }
    public String getTwitchDisabled() {
        return twitchDisabled;
    }
    public String getCleverBotDisabled() {
        return cleverBotDisabled;
    }
    public String getBwDisabled() {
        return bwDisabled;
    }
    public String getUserEventDisabled() {
        return userEventDisabled;
    }
    public String getServerSBDisabled() {
        return serverSBDisabled;
    }
    public String getTwitchChannel() {
        return twitchChannel;
    }
    public String getUserEventChannel() {
        return userEventChannel;
    }
    public String getMusicChannel() {
        return musicChannel;
    }
    public String getEmbedColor() {
        return embedColor;
    }


    public LoadingProperties() {
        try {
            File configFile = new File("config.blue");
            FileInputStream fileInput = new FileInputStream(configFile);
            Properties properties = new Properties();
            properties.load(fileInput);
            fileInput.close();

            botToken = properties.getProperty("botToken");
            botActivity = properties.getProperty("botActivity");
            cleverbotAPIKey = properties.getProperty("cleverbotAPIKey");
            botOwner = properties.getProperty("botOwner");

            streamerList = properties.getProperty("streamerList");
            autoRoleList = properties.getProperty("autoRoleList");

            badWords = properties.getProperty("badWords");
            prefixes = properties.getProperty("prefixes");

            twitchDisabled = properties.getProperty("twitchDisabled");
            cleverBotDisabled = properties.getProperty("cleverBotDisabled");
            bwDisabled = properties.getProperty("bwDisabled");
            userEventDisabled = properties.getProperty("userEventDisabled");
            serverSBDisabled = properties.getProperty("serverSBDisabled");

            twitchChannel = properties.getProperty("twitchChannel");
            userEventChannel = properties.getProperty("userEventChannel");
            musicChannel = properties.getProperty("musicChannel");

            embedColor = properties.getProperty("embedColor");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
