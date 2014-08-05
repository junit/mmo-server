package com.game.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import com.game.netty.Client;
import com.game.netty.coder.Decoder;
import com.game.netty.coder.Encoder;

public class GameClient extends Client {

	@Override
	protected boolean init() {
		return true;
	}

	@Override
	public void stop() {
	}

	@Override
	public ChannelInitializer<SocketChannel> getChannelInitializer() {
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new Encoder());
				ch.pipeline().addLast(new Decoder());
				ch.pipeline().addLast(new GameHandler());
			}
		};
	}

	public static void main(String[] args) {
		GameClient client = new GameClient();
		client.init("192.168.5.37", 5241);
	}
}
