package com.gameserverlive.commands.types;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public interface ServerCommand {

    public void performCommand(String[] args, Member m, TextChannel channel, Message message);

}
