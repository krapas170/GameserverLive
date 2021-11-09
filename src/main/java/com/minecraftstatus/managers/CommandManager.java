package com.minecraftstatus.managers;

import com.minecraftstatus.commands.ClearCommand;
import com.minecraftstatus.commands.ExitCommand;
import com.minecraftstatus.commands.HelpCommand_construct;
import com.minecraftstatus.commands.types.ServerCommand;
import java.util.concurrent.ConcurrentHashMap;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class CommandManager {
    
    private ConcurrentHashMap<String, ServerCommand> commands;
    
    public CommandManager() {
        commands = new ConcurrentHashMap<>();
        
        commands.put("clear", new ClearCommand());
        commands.put("exit", new ExitCommand());
        commands.put("help", new HelpCommand_construct());
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
