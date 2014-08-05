package com.game.login.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.game.message.struct.Handler;

public class ResLoginCreateRoleHandler extends Handler{
	private static Logger logger = Logger.getLogger(ResLoginCreateRoleHandler.class);
	private static AtomicInteger count = new AtomicInteger();
	
    @Override
    public void exec() {
        com.game.login.message.ResLoginCreateRoleMessage msg = (com.game.login.message.ResLoginCreateRoleMessage)this.getMessage();
        if (msg.getRet() != 0) {
        	logger.error("创建角色失败");
        }
        if (count.incrementAndGet() % 10 == 0) {
        	logger.error("当前创建角色完成数量:" + count);
        }
        this.getContext().close();
    }
}
