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

import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.dv8tion.jda.internal.requests.restaction.PermissionOverrideActionImpl;

public class GameServerManager extends TimerTask {

    private Timer timer;
    public ShardManager bot;
    private int time;

    public GameServerManager(ShardManager bot, int time) {
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
        for (Guild guild : bot.getGuilds()) {
			try {
				// onStartUP();
				Thread.sleep(500);
				updateGameserverStatus(guild);
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
			}
		}
    }

    private void updateGameserverStatus(Guild guild) throws SQLException, InterruptedException {
        ResultSet result = LiteSQL.onQuery("SELECT * FROM gameserver WHERE messageid = " + message.getIdLong());

		if (!result.next())
			return;

		long messageid = result.getLong("categoryid");
		Category category = guild.getCategoryById(messageid);

		updateMessage(category);

		// deleteChannels(category);
		// fillCategory(category);
    }

	private static boolean shouldDelete(String[] args) {
        return args.length == 2 && args[1].equalsIgnoreCase("delete");
    }

    private static void deleteMessage(Guild guild) throws SQLException {
    }

    private static void deleteChannels(Category category) {
        category.getChannels().forEach(channel -> {
            channel.delete().queue();
        });
    }

    public static void createCategory(Guild guild) throws InterruptedException {
    }

    public static void fillMessage(Message message) throws InterruptedException {
    }

    public static void sync(Category cat) {
        cat.getChannels().forEach(chan -> {
            chan.getManager().sync().queue();
        });
    }

    public static void updateMessage(Category cat) throws InterruptedException {
    }

    public static void onShutdown() {
    }

}