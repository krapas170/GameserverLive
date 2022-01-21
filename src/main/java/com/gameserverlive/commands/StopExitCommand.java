package com.gameserverlive.commands;

import com.gameserverlive.Console;
import com.gameserverlive.Main;
import com.gameserverlive.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class StopExitCommand implements ServerCommand{

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        ((ExitCommand) Main.getCommand("exit")).cancel();
        Console.info("canceling");
        message.delete().queue();
    }
}