package com.gameserverlive.commands;

import com.gameserverlive.Console;
import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;
import com.gameserverlive.configs.SettingsConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand {

    public final static SettingsConfig CONFIG = new SettingsConfig();

    @Override
    public void performCommand(String[] args, Member m, TextChannel channel, Message message) {

        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            ownerCommands(args, m, channel, message);
            Console.info("ownerCommand");
        }
        else if(m.hasPermission(Permission.MANAGE_THREADS)) {
            moderatorCommands(args, m, channel, message);
            Console.info("adminCommand");
        }
        else if(m.hasPermission(Permission.VIEW_CHANNEL)) {
            userCommands(args, m, channel, message);
            Console.info("userCommand");
        }
    }

    private void ownerCommands(String[] args, Member m, TextChannel channel, Message message) {
        String title = "This commands can you execute";
        String description = 
            "**🛠 __Admin Befehle__**\n" +
            //Admin
            "**" + CONFIG.getString("prefix") + "exit:** shutdown the Bot\n" +
            "**" + CONFIG.getString("prefix") + "abort:** abort the shutdown\n" +
            "**" + CONFIG.getString("prefix") + "config:** set the config for the bot\n" +
            //Moderator
            "**" + CONFIG.getString("prefix") + "clear <amount>:** clear messages\n" +
            "**" + CONFIG.getString("prefix") + "preview <message>:** send a message with the bot\n" +
            "**" + CONFIG.getString("prefix") + "stats:** create or update the statistics\n" +
            //User
            "**" + CONFIG.getString("prefix") + "ping:** show the ping of the bot\n" +
            "**" + CONFIG.getString("prefix") + "id:** show your Discord-ID\n" +
            "**" + CONFIG.getString("prefix") + "info:** show the info about the bot\n" +
            "**" + CONFIG.getString("prefix") + "play <link/search query>:** play an audio from youtube\n" +
            "**" + CONFIG.getString("prefix") + "stop:** stops the music and disconnect the bot from the voice channel\n" +
            "**" + CONFIG.getString("prefix") + "track:** displays the currently played track\n" +
            "**" + CONFIG.getString("prefix") + "shuffle:** enabled/disabled shuffle\n" + 
            "**" + CONFIG.getString("prefix") + "invite:** get an invite link for the bot\n";
        EmbedBuilder builder = new EmbedBuilder();
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    private void moderatorCommands(String[] args, Member m, TextChannel channel, Message message) {
        String title = "This commands can you execute";
        String description = 
            "**🛠 __Moderator Befehle__**\n" +
            //Moderator
            "**" + CONFIG.getString("prefix") + "clear <amount>:** clear messages\n" +
            "**" + CONFIG.getString("prefix") + "preview <message>:** send a message with the bot\n" +
            "**" + CONFIG.getString("prefix") + "stats:** create or update the statistics\n" +
            //User
            "**" + CONFIG.getString("prefix") + "ping:** show the ping of the bot\n" +
            "**" + CONFIG.getString("prefix") + "id:** show your Discord-ID\n" +
            "**" + CONFIG.getString("prefix") + "info:** show the info about the bot\n" +
            "**" + CONFIG.getString("prefix") + "play <link/search query>:** play an audio from youtube\n" +
            "**" + CONFIG.getString("prefix") + "stop:** stops the music and disconnect the bot from the voice channel\n" +
            "**" + CONFIG.getString("prefix") + "track:** displays the currently played track\n" +
            "**" + CONFIG.getString("prefix") + "shuffle:** enabled/disabled shuffle\n" +
            "**" + CONFIG.getString("prefix") + "invite:** get an invite link for the bot\n";
        EmbedBuilder builder = new EmbedBuilder();
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    private void userCommands(String[] args, Member m, TextChannel channel, Message message) {
        String title = "This commands can you execute";
        String description = 
            "**🛠 __User Befehle__**\n" +
            //User
            "**" + CONFIG.getString("prefix") + "ping:** show the ping of the bot\n" +
            "**" + CONFIG.getString("prefix") + "id:** show your Discord-ID\n" +
            "**" + CONFIG.getString("prefix") + "info:** show the info about the bot\n" +
            "**" + CONFIG.getString("prefix") + "play <link/search query>:** play an audio from youtube\n" +
            "**" + CONFIG.getString("prefix") + "stop:** stops the music and disconnect the bot from the voice channel\n" +
            "**" + CONFIG.getString("prefix") + "track:** displays the currently played track\n" +
            "**" + CONFIG.getString("prefix") + "shuffle:** enabled/disabled shuffle\n" +
            "**" + CONFIG.getString("prefix") + "invite:** get an invite link for the bot\n";
        EmbedBuilder builder = new EmbedBuilder();
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
