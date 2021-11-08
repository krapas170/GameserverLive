package com.minecraftstatus.commands;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.minecraftstatus.commands.types.ServerCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class ClearCommand implements ServerCommand {

    @Override
    public void performCommand(Member m, TextChannel channel, Message message) {
        try {
            delete(channel, message);
        } catch (Exception e) {
            channel.sendMessage("```There was an format error.\nYou need to write: !clear [number]```").complete().delete().queueAfter(5, TimeUnit.SECONDS);
        }
    }

    public void delete(TextChannel channel, Message message) {
        int amount = Integer.parseInt(message.getContentDisplay().split(" ")[1]);
        List<Message> messages = get(channel, amount);
        channel.purgeMessages(messages);
        channel.sendMessage("```" + amount + " Messages got deleted.```").complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }

    private List<Message> get(TextChannel channel, int amount) {
        int i = amount + 1;
        List<Message> messages = new LinkedList<>();

        for(Message message : channel.getIterableHistory().cache(false)) {
            if (!message.isPinned())
                messages.add(message);

            i--;

            if(i == 0)
                break;

        }
        return messages;

    }
    

    
}