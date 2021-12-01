package com.minecraftstatus.music;

import java.util.concurrent.ConcurrentHashMap;

import com.minecraftstatus.Console;
import com.minecraftstatus.Main;

public class PlayerManager {

	public ConcurrentHashMap<Long, MusicController> controller;
	
	public PlayerManager() {
		Console.test("open PlayerManager");
		this.controller = new ConcurrentHashMap<Long, MusicController>();
	}
	
	public MusicController getController(long guildid) {
		MusicController mc = null;
		
		if(this.controller.containsKey(guildid)) {
			mc = this.controller.get(guildid);
		}
		else {
			mc = new MusicController(Main.bot.getGuildById(guildid));
			
			this.controller.put(guildid, mc);
		}
		
		return mc;
	}
	
	public long getGuildByPlayerHash(int hash) {
		for(MusicController controller : this.controller.values()) {
			if(controller.getPlayer().hashCode() == hash) {
				return controller.getGuild().getIdLong();
			}
		}
		
		return -1;
	}
}
