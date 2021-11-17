package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;
import com.tekgator.queryminecraftserver.api.Protocol;
import com.tekgator.queryminecraftserver.api.QueryException;
import com.tekgator.queryminecraftserver.api.QueryStatus;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed.Field;
import net.dv8tion.jda.api.entities.TextChannel;


public class MCQueryCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        try {
            //queryResult = getMineCraftServerStatus();
            String query = new QueryStatus.Builder("mc.hypixel.net")
                                            .setProtocol(Protocol.TCP)
                                            .build()
                                            .getStatus()
                                            .toJson();
            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("GameserverLive");
            builder.setTitle("Status of Minecraft Server");
            //builder.setImage("");
            builder.setFooter("GameserverLive by pasi0104lp#9500 and cncptpr#0955", "https://pasi0104lp.de/wp-content/uploads/2021/11/server.png");
            //builder.setDescription(query.Builder.getDescription);
            builder.setColor(0xeb974e);
            builder.addField((Field) queryErrorMessage());                 
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

