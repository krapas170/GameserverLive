package com.minecraftstatus.music.commands;

import com.minecraftstatus.Main;
import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.music.MusicController;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class ShuffelCommand implements ServerCommand {

	@Override
	public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
		
		GuildVoiceState state;
		if((state = m.getVoiceState()) != null) {
			VoiceChannel vc;
			if((vc = state.getChannel()) != null) {
				MusicController controller = Main.playerManager.getController(vc.getGuild().getIdLong());
				controller.getQueue().shuffel();
				message.addReaction("U+1F500").queue();
			}
		}
	}
}
