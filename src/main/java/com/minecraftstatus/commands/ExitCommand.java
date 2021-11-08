package com.minecraftstatus.commands;

import net.dv8tion.jda.api.Permission;

import javax.swing.Timer;

import com.minecraftstatus.Main;
import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.MessageReaction.ReactionEmote;

public class ExitCommand implements ServerCommand{

    private final int time;

    public ExitCommand() {
        this(10);
    }

    public ExitCommand(int time) {
        this.time = time;
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            queuShutdown(channel);
        } else {
            sendInvalidCommandMessage();
        }
    }

    private void queuShutdown(TextChannel channel) {
        boolean cancle = sendShutdownMessage(channel);
        if (!cancle)
            System.exit(0);
    }

    private boolean sendShutdownMessage(TextChannel channel) {
        String messageText = "```\n" +
        "Shutting down GameserverLive in %time\n\n"+
        "Click the emoji to cancel\n" +
        "```";

        Message message = channel.sendMessage("").complete();
        message.addReaction(":octagonal_sign:");

        for (int i = time; i >= 0; i--) {
            editMessage(message, messageText, i);
            message.get
            
            try {
                wait(1000);
            } catch (InterruptedException ignore) {}
        }
    }

    private void editMessage(Message message, String messageText, int i) {
        message.editMessage(messageText.replaceAll("%time", "" + i)).queue();
    }
    
    

}
