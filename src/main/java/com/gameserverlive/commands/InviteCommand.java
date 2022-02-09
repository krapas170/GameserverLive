package com.gameserverlive.commands;

import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class InviteCommand implements ServerCommand {

    public String title = "Thanks for being interesting in our bot!";
    public String description = "Here is the long-awaited link to use the bot on other servers.\n\n Click this Link: https://gameserverlive.xyz/invite (Link is functioning soon)";

    EmbedBuilder builder = new EmbedBuilder();

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {

        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

}
