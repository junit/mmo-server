package com.game.map.handler;

import com.game.message.struct.Handler;
import com.game.account.struct.Account;
import org.apache.log4j.Logger;
import com.manager.ManagerPool;

public class ReqMapMoveHandler extends Handler{
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ReqMapMoveHandler.class);
    @Override
    public void exec() {
        com.game.map.message.ReqMapMoveMessage msg = (com.game.map.message.ReqMapMoveMessage)this.getMessage();
        Account account = ManagerPool.account.getAccount(this.getContext());
    }
}
