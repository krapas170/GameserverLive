package com.gameserverlive.managers;

import java.util.HashMap;

import com.gameserverlive.commands.ClearCommand;
import com.gameserverlive.commands.ClientIDCommand;
import com.gameserverlive.commands.ConfigCommand;
import com.gameserverlive.commands.ConstructCommand;
import com.gameserverlive.commands.CreatorinfoCommand;
import com.gameserverlive.commands.ExitCommand;
import com.gameserverlive.commands.HelpCommand;
import com.gameserverlive.commands.InviteCommand;
import com.gameserverlive.commands.MCQueryCommand;
import com.gameserverlive.commands.PingCommand;
import com.gameserverlive.commands.PreviewCommand;
import com.gameserverlive.commands.StatschannelCommand;
import com.gameserverlive.commands.StopExitCommand;
import com.gameserverlive.commands.types.ServerCommand;

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
        commands.put("mc", new MCQueryCommand());
        commands.put("ping", new PingCommand());
        commands.put("id", new ClientIDCommand());
        commands.put("info", new CreatorinfoCommand());
        commands.put("preview", new PreviewCommand());
        commands.put("abort", new StopExitCommand());
        commands.put("config", new ConfigCommand());
        commands.put("stats", new StatschannelCommand());
        commands.put("invite", new InviteCommand());
    }

    public ServerCommand getCommand(String key) {
        return commands.get(key);
    }

    public boolean perform(String command, String[] args, Member m, TextChannel channel, Message message) {

        ServerCommand cmd;
        if ((cmd = commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(args, m, channel, message);
            return true;
        }
        return false;
    }

}
