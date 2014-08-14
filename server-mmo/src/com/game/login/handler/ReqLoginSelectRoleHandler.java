package com.game.login.handler;

import org.apache.log4j.Logger;

import com.game.account.struct.Account;
import com.game.message.struct.Handler;
import com.manager.ManagerPool;

public class ReqLoginSelectRoleHandler extends Handler{
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ReqLoginSelectRoleHandler.class);
    @Override
    public void exec() {
        com.game.login.message.ReqLoginSelectRoleMessage msg = (com.game.login.message.ReqLoginSelectRoleMessage)this.getMessage();
        Account account = ManagerPool.account.getAccount(this.getContext());
        ManagerPool.login.login(account);
    }
}
