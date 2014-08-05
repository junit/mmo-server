package com.game.client;

import org.apache.log4j.Logger;

import io.netty.channel.ChannelHandlerContext;

import com.game.login.message.ReqLoginMessage;
import com.game.netty.handler.Handler;
import com.game.util.IdGenerator;

public class GameHandler extends Handler {
	private static Logger logger = Logger.getLogger(GameHandler.class);

	@Override
	public void onActive(ChannelHandlerContext ctx) {
		ReqLoginMessage msg = new ReqLoginMessage();
		msg.setAccountName(String.valueOf(IdGenerator.getId(1)));
		msg.setPlatform("37wan");
		msg.setProtocol("gasdgfdsgfdsgfd");
		msg.setServer(1);
		ctx.writeAndFlush(msg);
	}

	@Override
	public void onInactive(ChannelHandlerContext ctx) {
		logger.error("断开连接");
	}

	@Override
	public void onRecvMsg(com.game.message.struct.Handler handler) {
		handler.exec();
	}

}
