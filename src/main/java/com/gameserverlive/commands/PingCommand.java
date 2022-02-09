package com.gameserverlive.commands;

import com.gameserverlive.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PingCommand implements ServerCommand {

    public static final String emoji = "🏓";

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        long gatewayping = channel.getJDA().getGatewayPing();

        channel.getJDA().getRestPing()
                .queue((time) -> channel.sendMessageFormat("```Pong! %dms```", time, gatewayping).queue());

        message.addReaction(emoji).complete();

    }
}
