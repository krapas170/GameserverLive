package com.minecraftstatus.listener;

import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import com.minecraftstatus.Main;
import org.jetbrains.annotations.NotNull;

public class CommandListener extends ListenerAdapter{
    
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        
        String message = event.getMessage().getContentDisplay();
        
        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel channel = event.getTextChannel();
            if(message.startsWith("+")) {
                String[] args = message.substring(1).split(" ");

                if(args.length > 0) {
                    if(!Main.INSTANCE.getCmdMan().perform(args[0], event.getMember(), channel, event.getMessage())) {
                        channel.sendMessage("`Unknown Command. Please try again!`").queue();
                    }
                }
            }
        }
    }

    /*@Override
    public void onReactionAdded(MessageReactionAddEvent event) {
            
    }*/
}
