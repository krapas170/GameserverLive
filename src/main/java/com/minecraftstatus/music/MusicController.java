package com.minecraftstatus.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.minecraftstatus.Console;
import com.minecraftstatus.Main;
import net.dv8tion.jda.api.entities.Guild;

public class MusicController {

	
	private Guild guild;
	private AudioPlayer player;
	private Queue queue;
	
	public MusicController(Guild guild) {
		Console.test("open MusicController");
		this.guild = guild;
		this.player = Main.audioPlayerManager.createPlayer();
		this.queue = new Queue(this);
		
		this.guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(player));
		this.player.addListener(new TrackScheduler());
		this.player.setVolume(10);
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	public AudioPlayer getPlayer() {
		return player;
	}
	
	public Queue getQueue() {
		return queue;
	}
	
}
