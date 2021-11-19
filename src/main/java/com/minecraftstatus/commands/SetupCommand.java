package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.configs.DefaultConfig;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.Event;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SetupCommand implements ServerCommand{

    public final static DefaultConfig CONFIG = new DefaultConfig("config.json");

    @Override
	public void performCommand(Member member, TextChannel channel, Message message) {
	}
}
