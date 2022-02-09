package com.gameserverlive.managers;

import java.sql.ResultSet;

import com.gameserverlive.commands.types.EmbedMessage;
import com.gameserverlive.commands.types.ServerCommand;
import com.gameserverlive.managers.LiteSQL;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class GameServerManager implements ServerCommand {

    @Override
	public void performCommand(String[] args, Member m, TextChannel channel, Message message) {
		
		if(m.hasPermission(Permission.ADMINISTRATOR)) {
		
            Guild guild = channel.getGuild();
			ResultSet set = LiteSQL.onQuery("SELECT * FROM gameserver WHERE guildid = " + guild.getIdLong());
			ResultSet game = LiteSQL.onQuery("SELECT game FROM gameserver WHERE game = " + guild.getIdLong());

			ResultSet mc;
			ResultSet csgo;

            if(game == mc) {
				String title = "Status of Minecraft-Server %servername";
				String description = "MOTD: %mcdescription";
            }
            else if(game == csgo) {
				String title = "Status of CS:GO-Server %servername";
				String description = "Description: %csgodescription";
            }
            EmbedBuilder builder = new EmbedBuilder();
            String title = "Status of gameserver %servername";
            String description = "Description";
            EmbedMessage.run(title, description, channel);
            channel.sendMessageEmbeds(builder.build()).queue();
        }
    }
}