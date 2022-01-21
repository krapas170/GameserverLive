package com.gameserverlive.commands;

import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ConstructCommand implements ServerCommand {

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setImage("https://cdn5.vectorstock.com/i/1000x1000/16/09/coming-soon-neon-sign-with-megaphone-coming-soon-vector-22821609.jpg");
        String title = "Under Construction";
        String description = "This command will be create in the future. Please try again at another time!";
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }
}