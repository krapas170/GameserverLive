package com.gameserverlive.commands.types;

import com.gameserverlive.Console;
import com.gameserverlive.configs.SettingsConfig;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

public class EmbedMessage {

    public final static SettingsConfig CONFIG = new SettingsConfig();

    static EmbedBuilder builder = new EmbedBuilder();

    public static void run(String title, String description, TextChannel channel) {

        builder.setAuthor("GameserverLive");
        builder.setFooter("GameserverLive by pasi0104lp#9500 and cncptpr#0955",
                "https://pasi0104lp.de/wp-content/uploads/2021/11/server.png");

        builder.setTitle(title);
        builder.setDescription(description);
        builder.setColor(0xeb974e);

        channel.sendMessageEmbeds(builder.build()).queue();
    }
}