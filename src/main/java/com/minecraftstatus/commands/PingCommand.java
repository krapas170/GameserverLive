package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class PingCommand implements ServerCommand{

    public static final String emoji = "🏓";

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        long gatewayping = channel.getJDA().getGatewayPing();

        channel.getJDA().getRestPing().queue((time) ->
            channel.sendMessageFormat("```Pong! %dm```", time, gatewayping).queue()
        );

        message.addReaction(emoji).complete();
        
    }
}
