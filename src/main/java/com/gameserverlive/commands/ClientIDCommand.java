package com.gameserverlive.commands;

import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClientIDCommand implements ServerCommand {

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        String title = "Your ID";
        String description = m.getId();
        EmbedBuilder builder = new EmbedBuilder();
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();

    }

}
