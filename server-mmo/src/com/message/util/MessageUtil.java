package com.message.util;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.game.account.struct.Account;
import com.game.message.struct.Message;
import com.game.role.struct.Role;

public class MessageUtil {
	private static Logger logger = Logger.getLogger(MessageUtil.class);
	private static HashMap<Long, ChannelHandlerContext> contexts = new HashMap<>(); // accountId做key
	public static void add(long accountId, ChannelHandlerContext context) {
		contexts.put(accountId, context);
	}
	public static void remove(long accountId) {
		contexts.remove(accountId);
	}
	
	public static void send(Role role, Message msg) {
		send(role.getAccountId(), msg);
	}
	
	public static void send(Account account, Message msg) {
		send(account.getId(), msg);
	}
	
	public static void send(long accountId, Message msg) {
		ChannelHandlerContext context = contexts.get(accountId);
		if (context == null) {
			logger.error(new StringBuilder().append(accountId).append("连接不存在，发送消息失败").toString());
			return ;
		}
		send(context, msg);
	}
	
	public static void send(ChannelHandlerContext context, Message msg) {
		context.writeAndFlush(msg);
	}
}
