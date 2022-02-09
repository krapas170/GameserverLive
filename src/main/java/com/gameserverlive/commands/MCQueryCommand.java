package com.gameserverlive.commands;

import java.sql.ResultSet;
import java.util.List;

import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;
import com.gameserverlive.managers.LiteSQL;
import com.jayway.jsonpath.JsonPath;
import com.tekgator.queryminecraftserver.api.Protocol;
import com.tekgator.queryminecraftserver.api.QueryException;
import com.tekgator.queryminecraftserver.api.QueryStatus;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;


public class MCQueryCommand implements ServerCommand {

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        try {
            ResultSet adress = LiteSQL.onQuery("SELECT ipadress FROM gameserver WHERE textchannel = " + channel.getIdLong());
            String query = new QueryStatus.Builder("mc.hypixel.net")
                                            .setProtocol(Protocol.TCP)
                                            .build()
                                            .getStatus()
                                            .toJson();

            System.out.println(query);

            String json = query;

            String hostname = JsonPath.read(json, "$.server.hostname");
            String ipaddress = JsonPath.read(json, "$.server.ipaddress");
            int port = JsonPath.read(json, "$.server.port");
            int latency = JsonPath.read(json, "$.server.latency");
            int playeronline = JsonPath.read(json, "$.players.online");
            int playermax = JsonPath.read(json, "$.players.max");
            String beschreibung = JsonPath.read(json, "$.description");
            
            
            System.out.println("Hostname: " + hostname);
            System.out.println("\nIP Adresse: " + ipaddress);
            System.out.println("\nPort: " + port);
            System.out.println("\nLatenz: " + latency);
            System.out.println("\nPlayer online: " + playeronline);
            System.out.println("\nMaximale Spieler: " + playermax);
            System.out.println("\nBeschreibung: " + beschreibung);


            String title = "Status of Minecraft Server";
            String description =    "Hostname: **`" + hostname +
                                    "`**\nIP Adresse: **`" + ipaddress +
                                    "`**\nPort: **`" + port +
                                    "`**\nLatenz: **`" + latency + "ms" +
                                    "`**\nPlayer online: **`" + playeronline +
                                    "`**\nMaximale Spieler: **`" + playermax +
                                    "`**\nBeschreibung: **`" + beschreibung + "`**";
            EmbedBuilder builder = new EmbedBuilder();
            EmbedMessage.run(title, description, channel);
            channel.sendMessageEmbeds(builder.build()).queue();
            

        } catch (QueryException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private CharSequence queryErrorMessage() {
        return null;
    }
}

