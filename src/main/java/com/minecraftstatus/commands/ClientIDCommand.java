package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClientIDCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("GameserverLive");
        builder.setTitle("Your ID");
        //builder.setImage("");
        builder.setFooter("GameserverLive by pasi0104lp#9500 and cncptpr#0955", "https://pasi0104lp.de/wp-content/uploads/2021/11/server.png");
        builder.setDescription(m.getId());
        builder.setColor(0xeb974e);             
        channel.sendMessageEmbeds(builder.build()).queue();
        
    }
    
}
