package com.minecraftstatus.commands;

import com.minecraftstatus.Console;
import com.minecraftstatus.Main;
import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class StopExitCommand implements ServerCommand{

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        ((ExitCommand) Main.getCommand("exit")).cancel();
        Console.info("canceling");
        message.delete().queue();
    }
}