package com.message.manager;

import com.manager.Manager;
import com.game.message.MessagePool;

public class MessageManager extends Manager {

	@Override
	public boolean init() {
		<#list details as detail>
		MessagePool.getInstance().register(${detail.id?c}, ${detail.pkg}.handler.${detail.name}Handler.class, ${detail.pkg}.message.${detail.name}Message.class);
		</#list>
		return true;
	}

	@Override
	public void stop() {
	}

}
