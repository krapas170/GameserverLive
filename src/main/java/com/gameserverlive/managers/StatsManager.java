package com.gameserverlive.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.EnumSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.gameserverlive.Main;
import com.gameserverlive.commands.StatschannelCommand;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.internal.requests.restaction.PermissionOverrideActionImpl;

public class StatsManager extends TimerTask {

    private Timer timer;
    private ShardManager bot;
    private int time;

    public StatsManager(ShardManager bot, int time) {
        this.bot = bot;
        this.time = time;
        startTimer();
		run();
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(this, 0, time * 1000);
    }

    public void stopTimer() {
        timer.cancel();
    }

	@Override
	public void run() {
		for(Guild guild : bot.getGuilds()) {
			try {
				onStartUP();
				Thread.sleep(500);
				updateStats(guild);
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static boolean shouldDelete(String[] args) {
		return args.length == 2 && args[1].equalsIgnoreCase("delete");
	}

	public static void updateStats(Guild guild) throws SQLException, InterruptedException {
		ResultSet result = LiteSQL.onQuery("SELECT * FROM statchannels WHERE guildid = " + guild.getIdLong());
		
		if(!result.next())
			return;

		long categoryid = result.getLong("categoryid");
		Category category = guild.getCategoryById(categoryid);

		updateCategory(category);

		//deleteChannels(category);
		//fillCategory(category);
		
	}

	private static void deleteCategory(Guild guild) throws SQLException {
		ResultSet result = LiteSQL.onQuery("SELECT * FROM statchannels WHERE guildid = " + guild.getIdLong());
		long categoryid = result.getLong("categoryid");
		Category category = guild.getCategoryById(categoryid);
		LiteSQL.onUpdate("DELETE FROM statchannels WHERE guildid = " + guild.getIdLong());
		deleteChannels(category);
		category.delete().queue();
	}

	private static void deleteChannels(Category category) {
		category.getChannels().forEach(channel -> {
			channel.delete().queue();
		});
	}

	public static void createCategory(Guild guild) throws InterruptedException {
		Category category = guild.createCategory("Statistiken").complete();
		category.getManager().setPosition(-1).queue();
		PermissionOverride override = new PermissionOverrideActionImpl(category.getJDA(), category, category.getGuild().getPublicRole()).complete();
		category.getManager().putPermissionOverride(override.getRole(), null, EnumSet.of(Permission.VOICE_CONNECT)).queue();
		LiteSQL.onUpdate("INSERT INTO statchannels(guildid, categoryid) VALUES(" + guild.getIdLong() + ", " + category.getIdLong() + ")");
		fillCategory(category);
	}

    public static void fillCategory(Category category) throws InterruptedException {
		Thread.sleep(500);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Thread.sleep(500);
		SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.YYYY");
		Thread.sleep(500);
		category.createVoiceChannel("🕗 Uhrzeit: " + df.format(Calendar.getInstance().getTime()) + "Uhr").queue();
		Thread.sleep(500);
		category.createVoiceChannel("📅 Datum: " + df2.format(Calendar.getInstance().getTime())).queue();
		Thread.sleep(500);
		List<Member> members = category.getGuild().getMembers();
		Thread.sleep(500);
		category.createVoiceChannel("📈 Server Mitglieder: " + members.size()).queue();
		Thread.sleep(500);
		int online = 0;
		for(Member memb : members) {
			if(memb.getOnlineStatus() != OnlineStatus.ONLINE) {
				if(!memb.getUser().isBot()) {
					online++;
				}
			}
		}
		Thread.sleep(500);
		category.createVoiceChannel("🔘 Online User: " + online).queue();
		Thread.sleep(500);
		category.createVoiceChannel("✅ BOT ONLINE").queue();
		Thread.sleep(500);
		PermissionOverride override = new PermissionOverrideActionImpl(category.getJDA(), category, category.getGuild().getPublicRole()).complete();
		//System.out.println("OVerride: " + (override == null ? "NULL" : override.toString()));
		category.getManager().putPermissionOverride(override.getRole(), null, EnumSet.of(Permission.VOICE_CONNECT)).queue();
	}

	public static void sync(Category cat) {
		cat.getChannels().forEach(chan -> {
			chan.getManager().sync().queue();
		});
	}
	
	public static void updateCategory(Category cat) throws InterruptedException {
		if(cat.getChannels().size() == 5) {
			sync(cat);
			List<GuildChannel> channels = cat.getChannels();
			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			SimpleDateFormat df2 = new SimpleDateFormat("dd.MM.YYYY");
			channels.get(0).getManager().setName("🕗 Uhrzeit: " + df.format(Calendar.getInstance().getTime()) + "Uhr").queue();
			channels.get(1).getManager().setName("📅 Datum: " + df2.format(Calendar.getInstance().getTime())).queue();
			List<Member> members = cat.getGuild().getMembers();
			int online = 0;
			for(Member memb : members) {
				if(memb.getOnlineStatus() != OnlineStatus.ONLINE) {
					if(!memb.getUser().isBot()) {
						online++;
					}
				}
			}
			channels.get(2).getManager().setName("📈 Server Mitglieder: " + members.size()).queue();
			channels.get(3).getManager().setName("🔘 Online User: " + online).queue();
		}
	}
	
	public static void checkStats() {
		((ShardManager) Main.shardMan).getGuilds().forEach(guild -> {
			ResultSet set = LiteSQL.onQuery("SELECT categoryid FROM statchannels WHERE guildid = " + guild.getIdLong());
			try {
				if(set.next()) {
					long catid = set.getLong("categoryid");
					StatschannelCommand.updateCategory(guild.getCategoryById(catid));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}); 
	}
	
	public static void onStartUP() {
		((ShardManager) Main.shardMan).getGuilds().forEach(guild -> {
			ResultSet set = LiteSQL.onQuery("SELECT categoryid FROM statchannels WHERE guildid = " + guild.getIdLong());
			try {
				if(set.next()) {
					long catid = set.getLong("categoryid");
					Category cat = guild.getCategoryById(catid);
					cat.getChannels().forEach(chan -> {
						chan.delete().complete();
					});
					fillCategory(cat);
				}
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
			}
		}); 
	}
	
	public static void onShutdown() {
		((ShardManager) Main.shardMan).getGuilds().forEach(guild -> {
			ResultSet set = LiteSQL.onQuery("SELECT categoryid FROM statchannels WHERE guildid = " + guild.getIdLong());
			try {
				if(set.next()) {
					long catid = set.getLong("categoryid");
					Category cat = guild.getCategoryById(catid);
					cat.getChannels().forEach(chan -> {
						chan.delete().complete();
					});
					VoiceChannel offline = cat.createVoiceChannel("🔴 BOT OFFLINE").complete();
					PermissionOverride override = new PermissionOverrideActionImpl(cat.getJDA(), offline, cat.getGuild().getPublicRole()).complete();
					offline.getManager().putPermissionOverride(override.getRole(), null, EnumSet.of(Permission.VOICE_CONNECT)).queue();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}); 
	}

}
