package com.gameserverlive.commands;

import java.util.concurrent.TimeUnit;

import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PreviewCommand implements ServerCommand {

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        message.delete().queue();
        if (m.hasPermission(Permission.ADMINISTRATOR)) {
            String mess = message.getContentRaw().substring(9);
            String title = null;
            String description = mess;
            EmbedBuilder builder = new EmbedBuilder();
            EmbedMessage.run(title, description, channel);
            channel.sendMessageEmbeds(builder.build()).queue();
        } else {
            sendInvalidCommandMessage(channel);
        }
    }

    private void sendInvalidCommandMessage(TextChannel channel) {
        channel.sendMessage("```You don't have high enough permissions to execute this command.\n\nBitch!```")
                .complete().delete().queueAfter(10, TimeUnit.SECONDS);
    }
}
