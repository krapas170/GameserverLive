package com.gameserverlive.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.sharding.ShardManager;

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
        ResultSet result = LiteSQL.onQuery("SELECT * FROM gameserver WHERE guildid = " + guild.getIdLong());

		if (!result.next())
			return;

		long categoryid = result.getLong("categoryid");
		Category category = guild.getCategoryById(categoryid);

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