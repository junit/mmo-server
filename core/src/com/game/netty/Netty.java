package com.game.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public abstract class Netty extends ChannelHandlerAdapter {
    public abstract void stop();
    public abstract ChannelInitializer<SocketChannel> getChannelInitializer();
}
