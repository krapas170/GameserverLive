package com.minecraftstatus.commands;

import net.dv8tion.jda.api.Permission;

import javax.sound.sampled.SourceDataLine;
import javax.swing.Timer;

import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;

public class ExitCommand implements ServerCommand{

    private final int time;
    private final int timeMillis;
    private boolean cancel = false;
    private final String emoji = "🛑";

    public ExitCommand() {
        this(10);
    }

    public ExitCommand(int time) {
        this.time = time;
        this.timeMillis = time * 1000;
    }

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        if(m.hasPermission(Permission.ADMINISTRATOR)) {
            queueShutdown(channel);
        } else {
            sendInvalidCommandMessage(channel);
        }
    }

    private void sendInvalidCommandMessage(TextChannel channel) {
        channel.sendMessage("```You don't have high enough permissions to shut me down.\n\nBitch!```").complete().delete().queueAfter(10, TimeUnit.SECONDS);
    }

    private void queueShutdown(TextChannel channel) {
        Message message = sendShutdownMessage(channel);
        System.out.println("Shutdown Message send");
        startCancelListener(message);
        System.out.println("cancelListener");
        new Timer(timeMillis, e -> {
            if(cancel)
                return;
            System.out.println("stopping");
            message.delete().complete();
            System.exit(0);
        }).start();
    }

    private void startCancelListener(Message message) {
        for(MessageReaction reaction : message.getReactions())
            if(reaction.getReactionEmote().getEmoji().equals(emoji))
                startCancelListener(reaction);
    }

    private void startCancelListener(MessageReaction reaction) {
        new Thread(() -> {
            while (!cancel) {
                if(reaction.getCount() > 1)
                    cancel = true;
            }
        }).start();
    }

    

    private Message sendShutdownMessage(TextChannel channel) {
        String messageText = 
        "```\n" +
        "Shutting down GameserverLive in %time\n\n"+
        "Click the emoji to cancel\n" +
        "```";

        Message message = channel.sendMessage("Wait").complete();
        message.addReaction(emoji).queue();
        new Thread(() -> queueCountDown(message, messageText, time)).start();
        return message;
    }

    private void queueCountDown(Message message, String messageText, int time) {
        for(int i = 0; i < time; i++) {

            AtomicBoolean wait = new AtomicBoolean(true);
            new Timer(1000, e -> wait.set(false));

            while(wait.get());

            countDown(message, messageText, time - i);
            if (cancel) {
                sendCancelMessage(message);
                break;
            }
        }
    }

    private void countDown(Message message, String messageText, int i) {
        editMessage(message, messageText, i);
        System.out.println("Contdown: " + i);
    }

    private void sendCancelMessage(Message message) {
        message.editMessage("```Shutdown canceld```").queue();
        message.delete().queueAfter(5, TimeUnit.SECONDS);
    }

    private void editMessage(Message message, String messageText, int i) {
        message.editMessage(messageText.replaceAll("%time", "" + i)).complete();
    }

}
