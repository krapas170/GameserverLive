package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CreatorinfoCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0xeb974e);
        builder.setDescription("\n"
            + "Created by pasi0104lp#9500 and cncptpr#0955\n"
            + "Framework: JDA");
        channel.sendMessage(builder.build()).queue();
    }

    
}
