package com.minecraftstatus;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.configs.ServerConfig;
import com.minecraftstatus.configs.SettingsConfig;
import com.minecraftstatus.listener.CommandListener;
import com.minecraftstatus.listener.JoinListener;
import com.minecraftstatus.listener.VoiceListener;
import com.minecraftstatus.managers.CommandManager;
import com.minecraftstatus.managers.LiteSQL;
import com.minecraftstatus.managers.SQLManager;
import com.minecraftstatus.managers.StatusManager;
import com.minecraftstatus.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;


/*
*   The Main class for the bot
*   Starts the bot and owns all the Managers
*/
public class Main {
    
    public final static SettingsConfig CONFIG = new SettingsConfig();
    public final static ServerConfig SERVER = new ServerConfig();
    
    public static ShardManager bot;
    private static CommandManager commandManager;
    private static StatusManager statusManager;
    private static Thread loop;
    public static Object shardMan;
    public static DefaultAudioPlayerManager audioPlayerManager;
    public static PlayerManager playerManager;

    public static void main(String[] args) throws InterruptedException {
        try {
            init();
        } catch (LoginException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void init() throws LoginException, IllegalArgumentException, InterruptedException {
        audioPlayerManager = new DefaultAudioPlayerManager();
		playerManager = new PlayerManager();

        bot = buildBot();
        System.out.println("\n\n\nBot online!\n\n\n");
        statusManager = new StatusManager(bot, 3, "-w %server guilds | +help", "-l %members members | +help", "-w you.", "-l %voicechannels voice channels | +help", "-l %textchannels text channels | +help");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {shutdown(); System.out.println("Force shutdown!");}));
        startShutdownListener();
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
		audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);
        LiteSQL.connect();
		SQLManager.onCreate();
    }

    public static ServerCommand getCommand(String key) {
        return getCmdMan().getCommand(key);
    }

    /*
    *   Sets all default Variables and builds the bot
    */
    private static ShardManager buildBot() throws LoginException, IllegalArgumentException {
        DefaultShardManagerBuilder builder;
        builder = DefaultShardManagerBuilder.createDefault(CONFIG.getString("token"));
        builder.setStatus(OnlineStatus.ONLINE);
        commandManager = new CommandManager();
        builder.addEventListeners(new CommandListener());
		builder.addEventListeners(new VoiceListener());
		builder.addEventListeners(new JoinListener());
        return builder.build();
    }
    
    /*
    *   Returns the command Manager
    */
    public static CommandManager getCmdMan() {
        return commandManager;
    }

    public static void startShutdownListener() {
        new Thread(() -> {
            while(true) {
                Scanner scanner = new Scanner(System.in);
                if(scanner.nextLine().equalsIgnoreCase("exit")) {
                    if(bot != null) {
                        scanner.close();
                        System.exit(0);
                    }
                    if(loop != null) {
                        loop.interrupt();
                    }
                } else {
                    System.out.printf("\n\n\n%s[Bot] /sUse 'exit' to shutdown.%s\n\n\n", Console.RED, Console.BLUE, Console.RESET);
                }
            }
        }).start();
    }

    private static void shutdown() {
        statusManager.stopTimer();
        bot.setStatus(OnlineStatus.OFFLINE);
        bot.shutdown();
        LiteSQL.disconnect();
        System.out.printf("\n\n\n%s[Bot] %sshutdown!%s\n\n\n", Console.RED, Console.RED, Console.RESET);
        CONFIG.close();
        SERVER.close();
    }
}