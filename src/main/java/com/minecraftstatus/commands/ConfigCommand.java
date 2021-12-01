package com.minecraftstatus.commands;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.minecraftstatus.Main;
import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.configs.DefaultConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ConfigCommand implements ServerCommand {

    protected HashMap<String, String> getDefaultValue(HashMap<String, String> map) {
        return map;
    }

    public final static DefaultConfig CONFIG = new DefaultConfig("config.json");

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        
        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            if(args.length == 2) {
                printValue(channel, args[1]);
            } else if (args.length == 3) {
                writeValue(channel, args[1], args[2]);
            } else {
                channel.sendMessage("Wrong Syntax!\nWrite setup [key] to get the value.\nWrite setup [key] [value] to set the value.");
            }

        } else {
            sendInvalidCommandMessage(channel);
        }
    }

    private void printValue(TextChannel channel, String key) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0xeb974e);
        builder.setDescription("The value for " + key + " is " + Main.CONFIG.get(key));
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    private void writeValue(TextChannel channel, String key, String value) {
        Main.CONFIG.set(key, value);
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(0xeb974e);
        builder.setDescription("Changed the value for " + key + " to " + value);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    private void sendInvalidCommandMessage(TextChannel channel) {
        channel.sendMessage("```You don't have high enough permissions to execute this command.\n\nBitch!```").complete().delete().queueAfter(10, TimeUnit.SECONDS);
    }
}
