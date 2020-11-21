package bluebot.commands.misc;

import bluebot.MainBot;
import bluebot.utils.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.awt.*;

/**
 * @file SteamStatusCommand.java
 * @author Blue
 * @version 0.3
 * @brief Post Steam, CS:GO and Dota 2 current status
 */
public class SteamStatusCommand implements Command {

    private final String HELP = "The command `steamstatus` provides some information about Steam servers status. \n\nUsage : `!steamstatus`";

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        if(args.length != 0 && args[0].equals("help") || args.length != 0) {return false;}
        else return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        JSONObject result;
        OkHttpClient caller = new OkHttpClient();
        Request request = new Request.Builder().url("https://steamgaug.es/api/v2").build();

        try {
            Response response = caller.newCall(request).execute();
            result = new JSONObject(response.body().string());

            //Client
            JSONObject client = result.getJSONObject("ISteamClient");
            String cOnlineStatus = client.get("online").toString();
            if(cOnlineStatus.equals("1")) {cOnlineStatus = "Online";} else {cOnlineStatus = "Offline";}

            //Store
            JSONObject store = result.getJSONObject("SteamStore");
            String sOnlineStatus = store.get("online").toString();
            int sResponseTime = (int) store.get("time");
            if(sOnlineStatus.equals("1")) {sOnlineStatus = "Online";} else {sOnlineStatus = "Offline";}

            //Community
            JSONObject steamCommunity = result.getJSONObject("SteamCommunity");
            String sCOnlineStatus = steamCommunity.get("online").toString();
            int sCResponseTime = (int) steamCommunity.get("time");
            if(sCOnlineStatus.equals("1")) {sCOnlineStatus = "Online";} else {sCOnlineStatus = "Offline";}

            //Game coordinators
            JSONObject dota2 = result.getJSONObject("ISteamGameCoordinator").getJSONObject("570");
            String dota2OnlineStatus = dota2.get("online").toString();
            int dota2SearchingPlayers = (int) dota2.getJSONObject("stats").get("players_searching");
            if(dota2OnlineStatus.equals("1")) {dota2OnlineStatus = "Online";} else {dota2OnlineStatus = "Offline";}

            // CS:GO Matchmaking :
            JSONObject csgo = result.getJSONObject("ISteamGameCoordinator").getJSONObject("730");
            String csgoOnlineStatus = csgo.get("online").toString();
            int csgoSearchingPlayers = (int) csgo.getJSONObject("stats").get("players_searching");
            if(csgoOnlineStatus.equals("1")) {csgoOnlineStatus = "Online";} else {csgoOnlineStatus = "Offline";}

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("Steam, CS:GO & Dota 2 status", null, event.getAuthor().getAvatarUrl());
            builder.setColor(Color.decode(MainBot.getConfig().getEmbedColor()));
            builder.setThumbnail("https://i.imgur.com/7G9Ciep.jpg");

            builder.addBlankField(false);
            builder.addField("Steam Client", cOnlineStatus, false);
            builder.addField("Steam Store / Response time", sOnlineStatus + " / " + sResponseTime + " ms", false);
            builder.addField("Steam Community / Response time", sCOnlineStatus + " / " + sCResponseTime + " ms", false);
            builder.addBlankField(false);
            builder.addField("CS:GO Matchmaking / Players searching", csgoOnlineStatus + " / " + csgoSearchingPlayers, false);
            builder.addField("Dota 2 Matchmaking / Players searching", dota2OnlineStatus + " / " + dota2SearchingPlayers, false);


            event.getTextChannel().sendMessage(builder.build()).queue();
        } catch (Exception e) {
            e.printStackTrace();
            event.getTextChannel().sendMessage("There was an error contacting the API. Please try again later.").queue();
        }

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
