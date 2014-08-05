package com.game.login.handler;

import com.game.login.message.ReqLoginMessage;
import com.game.message.struct.Handler;
import com.manager.ManagerPool;

public class ReqLoginHandler extends Handler {

	@Override
	public void exec() {
		ManagerPool.login.login(this.getContext(), (ReqLoginMessage) this.getMessage());
	}

}
