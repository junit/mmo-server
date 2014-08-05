package com.server.game;

import io.netty.channel.ChannelHandlerContext;

import org.apache.log4j.Logger;

import com.game.account.struct.Account;
import com.game.netty.handler.Handler;
import com.manager.ManagerPool;
import com.message.util.MessageDispatcher;

public class GameHandler extends Handler {
	private static Logger logger = Logger.getLogger(GameHandler.class);
	private MessageDispatcher dispatcher = new MessageDispatcher();

	@Override
	public void onActive(ChannelHandlerContext ctx) {
		logger.error("建立连接成功:" + ctx.channel());
	}

	@Override
	public void onInactive(ChannelHandlerContext ctx) {
		logger.error("断开连接成功:" + ctx.channel());
		Account account = ManagerPool.account.unbind(ctx);
		if (account != null) {
			ManagerPool.account.onOffline(account);
		}
	}

	@Override
	public void onRecvMsg(com.game.message.struct.Handler handler) {
		dispatcher.dispatch(handler);
		
	}
}
