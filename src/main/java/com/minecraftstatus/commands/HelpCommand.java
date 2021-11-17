package com.minecraftstatus.commands;

import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class HelpCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        
        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor("pasi0104lp#9500");
        builder.setTitle("This commands can be execute");
        builder.setFooter("GameserverLive by pasi0104lp#9500 and cncptpr#0955", "https://pasi0104lp.de/wp-content/uploads/2021/11/server.png");
        builder.setDescription("The help command will be create in the future. Please try again at another time!");
        builder.setColor(0xeb974e);

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}
