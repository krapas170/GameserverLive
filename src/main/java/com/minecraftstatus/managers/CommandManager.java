package com.minecraftstatus.managers;

import java.util.HashMap;

import com.minecraftstatus.commands.ClearCommand;
import com.minecraftstatus.commands.ClientIDCommand;
import com.minecraftstatus.commands.ConstructCommand;
import com.minecraftstatus.commands.CreatorinfoCommand;
import com.minecraftstatus.commands.ExitCommand;
import com.minecraftstatus.commands.PingCommand;
import com.minecraftstatus.commands.PreviewCommand;
import com.minecraftstatus.commands.SetupCommand;
import com.minecraftstatus.commands.StopExitCommand;
import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {
    
    private HashMap<String, ServerCommand> commands;
    
    public CommandManager() {
        commands = new HashMap<>();
        
        commands.put("clear", new ClearCommand());
        commands.put("exit", new ExitCommand());
        commands.put("help", new ConstructCommand());
        commands.put("mc", new ConstructCommand());
        commands.put("ping", new PingCommand());
        commands.put("id", new ClientIDCommand());
        commands.put("information", new CreatorinfoCommand());
        commands.put("info", new CreatorinfoCommand());
        commands.put("preview", new PreviewCommand());
        commands.put("stop", new StopExitCommand());
        commands.put("setup", new SetupCommand());
    }

    public ServerCommand getCommand(String key) {
        return commands.get(key);
    }
    
    public boolean perform(String command, Member m, TextChannel channel, Message message) {
        
        ServerCommand cmd;
        if((cmd = commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(m, channel, message);
            return true;
        } 
        return false;
    }
    
}
