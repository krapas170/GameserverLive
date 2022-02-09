package com.gameserverlive.commands.types;

import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Invite.Channel;

public interface ReactionCommand {

    public void reacted(MessageReaction reaction, String messageId, User user, Channel channel);

}
