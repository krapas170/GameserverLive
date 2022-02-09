package com.gameserverlive.listener;

import com.gameserverlive.Main;
import com.gameserverlive.configs.DefaultConfig;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

	public final static DefaultConfig CONFIG = new DefaultConfig("config.json");

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getContentDisplay();

		if (event.isFromType(ChannelType.TEXT)) {

			TextChannel channel = event.getTextChannel();
			// !tut arg0 arg1 arg2 ...
			if (message.startsWith(CONFIG.getString("prefix"))) {
				String[] args = message.substring(CONFIG.getString("prefix").length()).split(" ");
				if (args.length > 0) {
					if (!Main.getCmdMan().perform(args[0], args, event.getMember(), channel, event.getMessage())) {
						channel.sendMessage("`Unknown command`").queue();
					}
				}
			}
		}
	}
}
