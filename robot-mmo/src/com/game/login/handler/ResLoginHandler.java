package com.game.login.handler;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.game.login.message.ReqLoginCreateRoleMessage;
import com.game.message.struct.Handler;

public class ResLoginHandler extends Handler{
	private static Logger logger = Logger.getLogger(ResLoginHandler.class);
	
	private static AtomicInteger count = new AtomicInteger();
	
    @Override
    public void exec() {
        com.game.login.message.ResLoginMessage msg = (com.game.login.message.ResLoginMessage)this.getMessage();
        if (msg.getRet() != 0) {
        	logger.error("登录失败");
        }
        if (count.incrementAndGet() % 10 == 0) {
        	logger.error("当前登录完成数量:" + count);
        }
        if (msg.getRoles() != null && !msg.getRoles().isEmpty()) {
        	return ;
        }
        
        ReqLoginCreateRoleMessage ret = new ReqLoginCreateRoleMessage();
        ret.setName("shell");
        this.getContext().writeAndFlush(ret);
    }
}
