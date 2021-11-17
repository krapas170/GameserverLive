package com.minecraftstatus.commands;

import net.dv8tion.jda.api.Permission;

import javax.swing.Timer;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.minecraftstatus.Console;
import com.minecraftstatus.commands.types.ReactionCommand;
import com.minecraftstatus.commands.types.ServerCommand;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Invite.Channel;

public class ExitCommand implements ServerCommand, ReactionCommand{

    private final int time;
    private final int timeMillis;
    private boolean cancel = false;
    private boolean running = false;
    private Message message;
    public static final String emoji = "🛑";

    public ExitCommand() {
        this(10);
    }

    public ExitCommand(int time) {
        this.time = time;
        this.timeMillis = time * 1000;
    }

    public void cancel() {
        cancel = true;
    }

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        if(running)
            return;

        message.delete().queue();
        if(member.hasPermission(Permission.ADMINISTRATOR)) {
            queueShutdown(channel);
        } else {
            sendInvalidCommandMessage(channel);
        }
    }

    private void sendInvalidCommandMessage(TextChannel channel) {
        channel.sendMessage("```You don't have high enough permissions to shut me down.\n\nBitch!```").complete().delete().queueAfter(10, TimeUnit.SECONDS);
    }

    private void queueShutdown(TextChannel channel) {
        running = true;
        sendShutdownMessage(channel);
        Console.info("Shutdown Message send!");
        //startCancelListener(message);
        new Timer(timeMillis, e -> performShutdown()).start();
    }

    private void performShutdown() {
        if(cancel)
            return;
        Console.println("%red%stopping");
        message.delete().complete();
        System.exit(0);
    }

    /*
    private MessageReaction getEmote(Message message) {
        for(MessageReaction reaction : message.getReactions()) {
            if(reaction.getReactionEmote().getEmoji().equals(emoji))
                return reaction;
            System.out.println(Console.BLUE + "Emoji" + reaction.getReactionEmote().getEmoji() + Console.RESET);
        }
        return null;
    }
    */

    private void sendShutdownMessage(TextChannel channel) {
        String messageText = 
        "```\n" +
        "Shutting down GameserverLive in %time\n\n"+
        "Click the emoji to cancel\n" +
        "```";

        message = channel.sendMessage("Wait").complete();
        //message.addReaction(emoji).complete();
        new Thread(() -> queueCountDown(messageText, time)).start();
    }

    private void queueCountDown(String messageText, int time) {
        Console.info("conting down");
        for(int i = 0; i < time; i++) {

            countDown(messageText, time - i);
            if (cancel) {
                sendCancelMessage(message);
                break;
            }

            //Just a timer
            //Somehow doesn't work in an other Method ¯\_(ツ)_/¯

            AtomicBoolean wait = new AtomicBoolean(true);
            new Timer(1000, e -> wait.set(false)).start();

            while(wait.get()) {

            }
        }
    }

    private void countDown(String messageText, int i) {
        editMessage(messageText, i);
        System.out.println("Contdown: " + i);
    }

    private void editMessage(String messageText, int i) {
        if(message != null)
            message.editMessage(messageText.replaceAll("%time", "" + i)).queue();
    }

    private void sendCancelMessage(Message message) {
        message.editMessage("```Shutdown canceld```").queue();
        message.delete().queueAfter(5, TimeUnit.SECONDS);
        running = false;
    }

    @Override
    public void reacted(MessageReaction reaction, String messageId, User user, Channel channel) {
        System.out.println("Emoji: Name: " + reaction.getReactionEmote().getName() + "; Emoji: " + reaction.getReactionEmote().getEmoji());
        if(reaction.getReactionEmote().getName().equals(emoji) 
            && messageId.equals(message.getId()) 
            && reaction.getCount() > 2) {
                cancel = true;
            }
    }

}
