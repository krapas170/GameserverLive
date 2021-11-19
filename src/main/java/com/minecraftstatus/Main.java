package com.minecraftstatus;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.configs.ServerConfig;
import com.minecraftstatus.configs.SettingsConfig;
import com.minecraftstatus.listener.CommandListener;
import com.minecraftstatus.managers.CommandManager;
import com.minecraftstatus.managers.LiteSQL;
import com.minecraftstatus.managers.SQLManager;
import com.minecraftstatus.managers.StatusManager;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;


/*
*   The Main class for the bot
*   Starts the bot and owns all the Managers
*/
public class Main {
    
    public static Main INSTANCE;
    public final static SettingsConfig CONFIG = new SettingsConfig();
    public final static ServerConfig SERVER = new ServerConfig();
    
    private ShardManager bot;
    private CommandManager commandManager;
    private StatusManager statusManager;
    private Thread loop;

    public static void main(String[] args) {
        try {
            new Main();
        } catch (LoginException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public Main() throws LoginException, IllegalArgumentException {
        INSTANCE = this;
        LiteSQL.connect();
		SQLManager.onCreate();
        bot = buildBot();
        System.out.println("\n\n\nBot online!\n\n\n");
        statusManager = new StatusManager(bot, 3, "-w %server guilds | +help", "-l %members members | +help", "-w you.", "-l %voicechannels voice channels | +help", "-l %textchannels text channels | +help");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {shutdown(); System.out.println("Force shutdown!");}));
        startShutdownListener();
    }

    public static ServerCommand getCommand(String key) {
        return INSTANCE.getCmdMan().getCommand(key);
    }

    /*
    *   Sets all default Variables and builds the bot
    */
    private ShardManager buildBot() throws LoginException, IllegalArgumentException {
        DefaultShardManagerBuilder builder;
        builder = DefaultShardManagerBuilder.createDefault(CONFIG.getString("token"));
        builder.setStatus(OnlineStatus.ONLINE);
        commandManager = new CommandManager();
        builder.addEventListeners(new CommandListener());
        return builder.build();
    }
    
    /*
    *   Returns the command Manager
    */
    public CommandManager getCmdMan() {
        return commandManager;
    }

    public void startShutdownListener() {
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

    private void shutdown() {
        statusManager.stopTimer();
        bot.setStatus(OnlineStatus.OFFLINE);
        bot.shutdown();
        LiteSQL.disconnect();
        System.out.printf("\n\n\n%s[Bot] %sshutdown!%s\n\n\n", Console.RED, Console.RED, Console.RESET);
        CONFIG.close();
    }
}