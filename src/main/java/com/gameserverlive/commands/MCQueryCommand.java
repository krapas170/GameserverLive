package com.gameserverlive.commands;

import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;
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
            //queryResult = getMineCraftServerStatus();
            String query = new QueryStatus.Builder("mc.hypixel.net")
                                            .setProtocol(Protocol.TCP)
                                            .build()
                                            .getStatus()
                                            .toJson();

            System.out.println(query);


            String title = "Status of Minecraft Server";
            String description = null;
            EmbedBuilder builder = new EmbedBuilder();
            EmbedMessage.run(title, description, channel);
            channel.sendMessageEmbeds(builder.build()).queue();

            System.out.println(query);
        }
        catch (QueryException e) {
            e.fillInStackTrace();
            message.reply(queryErrorMessage());
        }
    }

    private CharSequence queryErrorMessage() {
        return null;
    }
}

