package com.game.login.manager;

import io.netty.channel.ChannelHandlerContext;

import java.util.List;

import org.apache.log4j.Logger;

import com.game.account.struct.Account;
import com.game.login.message.ReqLoginMessage;
import com.game.login.message.ResLoginMessage;
import com.game.login.struct.LoginState;
import com.game.role.struct.Role;
import com.manager.Manager;
import com.manager.ManagerPool;
import com.manager.PriorityEnum;
import com.message.util.MessageUtil;

public class LoginManager extends Manager {
	@SuppressWarnings("unused")
	private Logger logger = Logger.getLogger(this.getClass());

	@Override
	public boolean init() {
		return true;
	}
	
	@Override
	public PriorityEnum getPriority() {
		return PriorityEnum.NORMAL;
	}

	@Override
	public void stop() {
	}

	public void login(ChannelHandlerContext context, ReqLoginMessage message) {
		if (!ManagerPool.account.checkProtocol(message.getPlatform(), message.getProtocol())) {
			ResLoginMessage ret = new ResLoginMessage();
			ret.setRet((byte) 1);
			MessageUtil.send(context, ret);
			context.close();
			return;
		}

		Account account = ManagerPool.account.getAccount(message.getPlatform(), message.getServer(), message.getAccountName());
		if (account == null) {
			account = ManagerPool.account.createAccount(message.getPlatform(), message.getServer(), message.getAccountName());
		}

		account.setLoginTime(System.currentTimeMillis());
		List<Role> roles = ManagerPool.role.getRoleByAccountId(account.getId());

		ResLoginMessage ret = new ResLoginMessage();
		if (roles != null) {
			for (Role role : roles) {
				account.getRoles().put(role.getId(), role);
				ret.getRoles().add(role.getBriefInfo());
			}
		}

		ManagerPool.account.bind(context, account);

		ret.setRet((byte) 0);
		MessageUtil.send(context, ret);

		ManagerPool.loginState.change(context, LoginState.LOGIN_END);
	}

	public void login(Account account) {
		ManagerPool.map.login(account);
	}
}
