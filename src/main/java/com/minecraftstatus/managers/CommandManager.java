package com.minecraftstatus.managers;

import java.util.HashMap;

import com.minecraftstatus.commands.ClearCommand;
import com.minecraftstatus.commands.ClientIDCommand;
import com.minecraftstatus.commands.ConstructCommand;
import com.minecraftstatus.commands.CreatorinfoCommand;
import com.minecraftstatus.commands.ExitCommand;
import com.minecraftstatus.commands.HelpCommand;
import com.minecraftstatus.commands.PingCommand;
import com.minecraftstatus.commands.PreviewCommand;
import com.minecraftstatus.commands.ConfigCommand;
import com.minecraftstatus.commands.StatschannelCommand;
import com.minecraftstatus.commands.StopExitCommand;
import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.music.commands.PlayCommand;
import com.minecraftstatus.music.commands.ShuffelCommand;
import com.minecraftstatus.music.commands.StopCommand;
import com.minecraftstatus.music.commands.TrackInfoCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {
    
    private HashMap<String, ServerCommand> commands;
    
    public CommandManager() {
        commands = new HashMap<>();
        
        commands.put("clear", new ClearCommand());
        commands.put("exit", new ExitCommand());
        commands.put("help", new HelpCommand());
        commands.put("mc", new ConstructCommand());
        commands.put("ping", new PingCommand());
        commands.put("id", new ClientIDCommand());
        commands.put("information", new CreatorinfoCommand());
        commands.put("info", new CreatorinfoCommand());
        commands.put("preview", new PreviewCommand());
        commands.put("abort", new StopExitCommand());
        commands.put("config", new ConfigCommand());
        commands.put("stats", new StatschannelCommand());
        commands.put("play", new PlayCommand());
		commands.put("stop", new StopCommand());
		commands.put("track", new TrackInfoCommand());
		commands.put("shuffle", new ShuffelCommand());
    }

    public ServerCommand getCommand(String key) {
        return commands.get(key);
    }
    
    public boolean perform(String command, String[] args, Member m, TextChannel channel, Message message) {
        
        ServerCommand cmd;
        if((cmd = commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(args, m, channel, message);
            return true;
        } 
        return false;
    }
    
}
