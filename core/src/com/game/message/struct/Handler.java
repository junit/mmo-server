package com.game.message.struct;

import io.netty.channel.ChannelHandlerContext;

/**
 * Created by game on 3/31/14.
 */
public abstract class Handler {
    private Message message; // message
    private ChannelHandlerContext context;

    public ChannelHandlerContext getContext() {
        return context;
    }

    public void setContext(ChannelHandlerContext context) {
        this.context = context;
    }

    public abstract void exec();

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
