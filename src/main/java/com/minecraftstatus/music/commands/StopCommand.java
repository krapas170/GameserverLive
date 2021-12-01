package com.minecraftstatus.music.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

import com.minecraftstatus.Main;
import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.music.MusicController;
import com.minecraftstatus.music.MusicUtil;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class StopCommand implements ServerCommand {

	@Override
	public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
		
		GuildVoiceState state;
		if((state = m.getVoiceState()) != null) {
			VoiceChannel vc;
			if((vc = state.getChannel()) != null) {
				MusicController controller = Main.playerManager.getController(vc.getGuild().getIdLong());
				AudioManager manager = vc.getGuild().getAudioManager();
				AudioPlayer player = controller.getPlayer();
				MusicUtil.updateChannel(channel);
				player.stopTrack();
				manager.closeAudioConnection();
				message.addReaction("U+1F44C").queue();
			}
		}	
	}
}
