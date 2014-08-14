package com.message.manager;

import com.manager.Manager;
import com.manager.PriorityEnum;
import com.game.message.MessagePool;

public class MessageManager extends Manager {

	@Override
	public boolean init() {
				MessagePool.getInstance().register(100101, com.game.login.handler.ReqLoginSelectRoleHandler.class, com.game.login.message.ReqLoginSelectRoleMessage.class);
		MessagePool.getInstance().register(100100, com.game.login.handler.ReqLoginHandler.class, com.game.login.message.ReqLoginMessage.class);
		MessagePool.getInstance().register(100102, com.game.login.handler.ReqLoginCreateRoleHandler.class, com.game.login.message.ReqLoginCreateRoleMessage.class);
		MessagePool.getInstance().register(103100, com.game.map.handler.ReqMapMoveHandler.class, com.game.map.message.ReqMapMoveMessage.class);
		return true;
	}

	@Override
	public void stop() {
	}

	@Override
	public PriorityEnum getPriority() {
		return PriorityEnum.BASE;
	}
}
