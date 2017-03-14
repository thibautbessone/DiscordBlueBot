package bluebot.commands.misc;

import bluebot.utils.Command;
import com.mashape.unirest.http.Unirest;
import net.dv8tion.jda.events.message.MessageReceivedEvent;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

/**
 * Created by Thibaut on 14/03/2017.
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
        String message = "";
        JSONObject result;

        try {
            result = Unirest.get("https://steamgaug.es/api/v2").asJson().getBody().getObject();

            //Client
            JSONObject client = result.getJSONObject("ISteamClient");
            String cOnlineStatus = client.get("online").toString();
            if(cOnlineStatus.equals("1")) {cOnlineStatus = "online";} else {cOnlineStatus = "offline";}
            message += "Steam client : `" + cOnlineStatus + "`\n\n";

            //Store
            JSONObject store = result.getJSONObject("SteamStore");
            String sOnlineStatus = store.get("online").toString();
            int sResponseTime = (int) store.get("time");
            if(sOnlineStatus.equals("1")) {sOnlineStatus = "online";} else {sOnlineStatus = "offline";}
            message += "Steam Store : `" + sOnlineStatus +"`\nResponse time : `" + sResponseTime + " ms`\n\n";

            //Community
            JSONObject steamCommunity = result.getJSONObject("SteamCommunity");
            String sCOnlineStatus = steamCommunity.get("online").toString();
            int sCResponseTime = (int) steamCommunity.get("time");
            if(sCOnlineStatus.equals("1")) {sCOnlineStatus = "online";} else {sCOnlineStatus = "offline";}
            message += "Steam Community : `" + sCOnlineStatus +"`\nResponse time : `" + sCResponseTime + " ms`\n\n";
                       // CS:GO Matchmaking :

            //Game coordinators
            JSONObject dota2 = result.getJSONObject("ISteamGameCoordinator").getJSONObject("570");
            String dota2OnlineStatus = dota2.get("online").toString();
            int dota2SearchingPlayers = (int) dota2.getJSONObject("stats").get("players_searching");
            if(dota2OnlineStatus.equals("1")) {dota2OnlineStatus = "online";} else {dota2OnlineStatus = "offline";}
            message += "Dota 2 Matchmaking : `" + dota2OnlineStatus + "`, players searching : `" + dota2SearchingPlayers + "`\n";

            JSONObject csgo = result.getJSONObject("ISteamGameCoordinator").getJSONObject("730");
            String csgoOnlineStatus = csgo.get("online").toString();
            int csgoSearchingPlayers = (int) csgo.getJSONObject("stats").get("players_searching");
            if(csgoOnlineStatus.equals("1")) {csgoOnlineStatus = "online";} else {csgoOnlineStatus = "offline";}
            message += "CS:GO Matchmaking : `" + csgoOnlineStatus + "`, players searching : `" + csgoSearchingPlayers + "`\n";

            event.getTextChannel().sendMessage(message);

        } catch (Exception e) {
            e.printStackTrace();
            event.getTextChannel().sendMessage("There was an error contacting the API. Please try again later");
        }

    }

    @Override
    public String help() {
        return HELP;
    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        if(!success) {
            event.getTextChannel().sendMessage(help());
        }
    }
}
