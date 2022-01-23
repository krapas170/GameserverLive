package com.gameserverlive;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.gameserverlive.commands.types.ServerCommand;
import com.gameserverlive.configs.ServerConfig;
import com.gameserverlive.configs.SettingsConfig;
import com.gameserverlive.listener.CommandListener;
import com.gameserverlive.listener.JoinListener;
import com.gameserverlive.listener.VoiceListener;
import com.gameserverlive.managers.CommandManager;
import com.gameserverlive.managers.LiteSQL;
import com.gameserverlive.managers.SQLManager;
import com.gameserverlive.managers.StatsManager;
import com.gameserverlive.managers.StatusManager;

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

    public static void main(String[] args) throws InterruptedException {
        try {
            init();
        } catch (LoginException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public static void init() throws LoginException, IllegalArgumentException, InterruptedException {

        bot = buildBot();
        System.out.println("\n\n\nBot online!\n\n\n");
        statusManager = new StatusManager(bot, 20, "-w %server guilds | +help", "-l %members members | +help", "-w you.", "-l %voicechannels voice channels | +help", "-l %textchannels text channels | +help");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {try {
            shutdown();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } System.out.println("Force shutdown!");}));
        startShutdownListener();
        LiteSQL.connect();
		SQLManager.onCreate();
        Thread.sleep(5000);
        new StatsManager(bot, 30);
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

    private static void shutdown() throws InterruptedException {
        //onShutdown();
        StatsManager.onShutdown();
        Thread.sleep(1000);
        statusManager.stopTimer();
        bot.setStatus(OnlineStatus.OFFLINE);
        bot.shutdown();
        LiteSQL.disconnect();
        System.out.printf("\n\n\n%s[Bot] %sshutdown!%s\n\n\n", Console.RED, Console.RED, Console.RESET);
        CONFIG.close();
        SERVER.close();
    }
}
