package com.game.login.handler;

import org.apache.log4j.Logger;

import com.game.message.struct.Handler;

public class ReqLoginSelectRoleHandler extends Handler{
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ReqLoginSelectRoleHandler.class);
    @Override
    public void exec() {
        com.game.login.message.ReqLoginSelectRoleMessage msg = (com.game.login.message.ReqLoginSelectRoleMessage)this.getMessage();
        // 这个消息很特殊，仅仅处理进入游戏就可以了。
    }
}
