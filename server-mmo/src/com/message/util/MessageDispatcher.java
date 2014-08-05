package com.message.util;

import org.apache.log4j.Logger;

import com.game.account.struct.Account;
import com.game.login.message.ReqLoginSelectRoleMessage;
import com.game.login.struct.LoginState;
import com.game.map.thread.MapThread;
import com.game.role.struct.Role;
import com.game.thread.pool.NormalThread;
import com.manager.ManagerPool;

public class MessageDispatcher {
	private static Logger logger = Logger.getLogger(MessageDispatcher.class);

	public void dispatch(com.game.message.struct.Handler handler) {
		try {
			switch (handler.getMessage().getId()) {
			case 100100:
				login(handler);
				break;
			case 100102:
				createRole(handler);
				break;
			case 100101:
				selectRole(handler);
				break;
			default:
				exec(handler);
				break;
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	private void login(com.game.message.struct.Handler handler) {
		if (!ManagerPool.loginState.change(handler.getContext(), LoginState.LOGIN_ING)) {
			handler.getContext().close();
			return;
		}

		ManagerPool.thread.getLoginExecutor().execute(new NormalThread(handler));

	}

	private void exec(com.game.message.struct.Handler handler) {
		if (!ManagerPool.loginState.check(handler.getContext(), LoginState.SELECT_ROLE_END)) {
			handler.getContext().close();
			return;
		}

		Account account = ManagerPool.account.getAccount(handler.getContext());
		if (account == null) {
			handler.getContext().close();
			return;
		}
		
		if (account.getRole() == null) {
			handler.getContext().close();
			return;
		}
		
		MapThread thread = ManagerPool.map.getMapThread(account.getRole().getMap());
		thread.addTask(handler);
	}

	private void selectRole(com.game.message.struct.Handler handler) {
		if (!ManagerPool.loginState.change(handler.getContext(), LoginState.SELECT_ROLE_ING)) {
			handler.getContext().close();
			return;
		}
		
		Account account = ManagerPool.account.getAccount(handler.getContext());
		if (account == null) {
			handler.getContext().close();
			return;
		}

		if (account.getRole() != null) {
			handler.getContext().close();
			return;
		}

		Role role = account.getRoles().get(((ReqLoginSelectRoleMessage)handler.getMessage()).getRoleId());
		if (role == null) {
			handler.getContext().close();
			return;
		}

		account.setRole(role);

		exec(handler);
	}

	private void createRole(com.game.message.struct.Handler handler) {
		if (!ManagerPool.loginState.change(handler.getContext(), LoginState.CREATE_ROLE_ING)) {
			handler.getContext().close();
			return;
		}
		handler.exec();
	}
}
