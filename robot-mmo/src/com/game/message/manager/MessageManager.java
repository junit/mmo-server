package com.game.message.manager;

import com.game.manager.Manager;
import com.game.message.MessagePool;

public class MessageManager extends Manager {

	@Override
	public boolean init() {
		MessagePool.getInstance().register(100201, com.game.login.handler.ResLoginCreateRoleHandler.class,
				com.game.login.message.ResLoginCreateRoleMessage.class);
		MessagePool.getInstance().register(100200, com.game.login.handler.ResLoginHandler.class, com.game.login.message.ResLoginMessage.class);
		return true;
	}

	@Override
	public void stop() {
	}

}
