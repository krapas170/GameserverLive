package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand_construct implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        channel.sendMessage("The help command will be create in the future. Please try again at another time").queue();
    }
}
