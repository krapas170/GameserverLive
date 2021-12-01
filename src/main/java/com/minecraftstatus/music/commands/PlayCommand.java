package com.minecraftstatus.music.commands;

import java.awt.Color;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.minecraftstatus.Console;
import com.minecraftstatus.Main;
import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.music.AudioLoadResult;
import com.minecraftstatus.music.MusicController;
import com.minecraftstatus.music.MusicUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

public class PlayCommand implements ServerCommand {

	@Override
	public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
		
		if(args.length > 1) {
			GuildVoiceState state = m.getVoiceState();
			if(state != null) {
				VoiceChannel vc = state.getChannel();
				if(vc != null) {
					MusicController controller = Main.playerManager.getController(vc.getGuild().getIdLong());
					AudioPlayerManager apm = Main.audioPlayerManager;

					AudioManager manager = vc.getGuild().getAudioManager();
					manager.openAudioConnection(vc);

					Console.test("open Audio Connection");
					
					MusicUtil.updateChannel(channel);
					
					StringBuilder strBuilder = new StringBuilder();
					for(int i = 1; i < args.length; i++) strBuilder.append(args[i] + " ");
					
					String url = strBuilder.toString().trim();
					if(!url.startsWith("http")) {
						url = "ytsearch: " + url;
					}
					
					apm.loadItem(url, new AudioLoadResult(controller, url));
				}
				else {
					sendErrorMessage("Huch? Du bist wohl in keinem VoiceChannel.", channel);
				}
			}
			else {
				sendErrorMessage("Huch? Du bist wohl in keinem VoiceChannel.", channel);
			}
		}
		else {
			sendErrorMessage("Bitte benutze !play <url/search query>", channel);
		}
	}

	public void sendErrorMessage(String message, TextChannel channel) {
		
		EmbedBuilder builder = new EmbedBuilder();
		builder.setDescription("message");
		builder.setColor(Color.decode("#f22613"));
		channel.sendMessageEmbeds(builder.build()).queue();
	}
}
