package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;
import com.minecraftstatus.configs.SettingsConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand {

    public final static SettingsConfig CONFIG = new SettingsConfig();

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
        
        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor("pasi0104lp#9500");
        builder.setTitle("This commands can be execute");
        builder.setFooter("GameserverLive by pasi0104lp#9500 and cncptpr#0955", "https://pasi0104lp.de/wp-content/uploads/2021/11/server.png");
        builder.setDescription(
            "**" + CONFIG.getString("prefix") + "help:** show this message\n" +
            "**" + CONFIG.getString("prefix") + "clear <amount>:** clear messages\n" +
            "**" + CONFIG.getString("prefix") + "exit:** shutdown the Bot\n" +
            "**" + CONFIG.getString("prefix") + "abort:** abort the shutdown\n" +
            "**" + CONFIG.getString("prefix") + "ping:** show the ping of the bot\n" +
            "**" + CONFIG.getString("prefix") + "id:** show your Discord-ID\n" +
            "**" + CONFIG.getString("prefix") + "info:** show the info about the bot\n" +
            "**" + CONFIG.getString("prefix") + "preview <message>:** send a message with the bot\n" +
            "**" + CONFIG.getString("prefix") + "config:** set the config for the bot\n" +
            "**" + CONFIG.getString("prefix") + "stats:** create or update the statistics\n" +
            "**" + CONFIG.getString("prefix") + "play <link/search query>:** play an audio from youtube\n" +
            "**" + CONFIG.getString("prefix") + "stop:** stops the music and disconnect the bot from the voice channel\n" +
            "**" + CONFIG.getString("prefix") + "track:** displays the currently played track\n" +
            "**" + CONFIG.getString("prefix") + "shuffle:** enabled/disabled shuffle\n"
        );

        builder.setColor(0xeb974e);

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
