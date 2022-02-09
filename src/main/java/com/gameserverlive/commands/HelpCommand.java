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

        if (m.hasPermission(Permission.ADMINISTRATOR)) {
            ownerCommands(args, m, channel, message);
            Console.info("ownerCommand");
        } else if (m.hasPermission(Permission.MANAGE_THREADS)) {
            moderatorCommands(args, m, channel, message);
            Console.info("adminCommand");
        } else if (m.hasPermission(Permission.VIEW_CHANNEL)) {
            userCommands(args, m, channel, message);
            Console.info("userCommand");
        }
    }

    private void ownerCommands(String[] args, Member m, TextChannel channel, Message message) {
        String title = "This commands can you execute";
        String description = "**🛠 __Admin Befehle__**\n\n" +
                "**Prefix:** " + CONFIG.getString("prefix") + ("\n") +
                // Admin
                "**exit:** shutdown the Bot\n" +
                "**abort:** abort the shutdown\n" +
                "**config:** set the config for the bot\n" +
                // Moderator
                "**clear <amount>:** clear messages\n" +
                "**preview <message>:** send a message with the bot\n" +
                "**stats:** create or update the statistics\n" +
                // User
                "**ping:** show the ping of the bot\n" +
                "**id:** show your Discord-ID\n" +
                "**info:** show the info about the bot\n" +
                "**invite:** get an invite link for the bot\n";
        EmbedBuilder builder = new EmbedBuilder();
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    private void moderatorCommands(String[] args, Member m, TextChannel channel, Message message) {
        String title = "This commands can you execute";
        String description = "**🛠 __Moderator Befehle__**\n" +
                "**Prefix:** " + CONFIG.getString("prefix") + ("\n") +
                // Moderator
                "**clear <amount>:** clear messages\n" +
                "**preview <message>:** send a message with the bot\n" +
                "**stats:** create or update the statistics\n" +
                // User
                "**ping:** show the ping of the bot\n" +
                "**id:** show your Discord-ID\n" +
                "**info:** show the info about the bot\n" +
                "**invite:** get an invite link for the bot\n";
        EmbedBuilder builder = new EmbedBuilder();
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }

    private void userCommands(String[] args, Member m, TextChannel channel, Message message) {
        String title = "This commands can you execute";
        String description = "**🛠 __User Befehle__**\n" +
                "**Prefix:** " + CONFIG.getString("prefix") + ("\n") +
                // User
                "**ping:** show the ping of the bot\n" +
                "**id:** show your Discord-ID\n" +
                "**info:** show the info about the bot\n" +
                "**invite:** get an invite link for the bot\n";
        EmbedBuilder builder = new EmbedBuilder();
        EmbedMessage.run(title, description, channel);
        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
