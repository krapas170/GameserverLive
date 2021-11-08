package com.minecraftstatus;

import com.minecraftstatus.listener.CommandListener;
import com.minecraftstatus.managers.CommandManager;
import com.minecraftstatus.managers.StatusManager;

import java.util.Scanner;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;


/*
*   The Main class for the bot
+   Starts the bot and owns all the Managers
*/
public class Main {
    
    public static Main INSTANCE;
    
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
        bot = buildBot();
        System.out.println("\n\n\nBot online!\n\n\n");
        statusManager = new StatusManager(bot, 10, "-w %server guilds | +help", "-l %members member | +help", "-w you.");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {shutdown(); System.out.println("Force shutdown!");}));
        startShutdownListener();
    }

    /*
    *   Sets all default Variables and builds the bot
    */
    private ShardManager buildBot() throws LoginException, IllegalArgumentException {
        DefaultShardManagerBuilder builder;
        builder = DefaultShardManagerBuilder.createDefault(DCToken.TOKEN);
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
        System.out.printf("\n\n\n%s[Bot] %sshutdown!%s\n\n\n", Console.RED, Console.RED, Console.RESET);
    }
}