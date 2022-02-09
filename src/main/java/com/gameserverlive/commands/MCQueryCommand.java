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
            ResultSet address = LiteSQL.onQuery("SELECT ipadress FROM gameserver WHERE textchannel = " + channel.getIdLong());
            String query = new QueryStatus.Builder("mc.hypixel.net")
                                            .setProtocol(Protocol.TCP)
                                            .build()
                                            .getStatus()
                                            .toJson();

            String hostname = JsonPath.read(query, "$.server.hostname");
            String ipaddress = JsonPath.read(query, "$.server.ipaddress");
            int port = JsonPath.read(query, "$.server.port");
            int latency = JsonPath.read(query, "$.server.latency");
            int playeronline = JsonPath.read(query, "$.players.online");
            int playermax = JsonPath.read(query, "$.players.max");
            String beschreibung = JsonPath.read(query, "$.description");

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

