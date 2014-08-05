package com.game.login.manager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import com.game.login.struct.LoginState;
import com.manager.Manager;

public class LoginStateManager extends Manager {
	private static final String LOGIN_STATE = "LOGIN_STATE";

	@Override
	public boolean init() {
		return true;
	}

	@Override
	public void stop() {
	}

	public boolean change(ChannelHandlerContext context, LoginState state) {
		synchronized (context) {
			LoginState nowState = (LoginState)context.attr(AttributeKey.valueOf(LOGIN_STATE)).get();
			switch (state) {
			case LOGIN_ING:
				if (nowState == null) {
					context.attr(AttributeKey.valueOf(LOGIN_STATE)).set(state);
					return true;
				}
			case LOGIN_END:
				if (nowState == LoginState.LOGIN_ING) {
					context.attr(AttributeKey.valueOf(LOGIN_STATE)).set(state);
					return true;
				}
			case CREATE_ROLE_ING:
				if (nowState == LoginState.LOGIN_END) {
					context.attr(AttributeKey.valueOf(LOGIN_STATE)).set(state);
					return true;
				}
			case CREATE_ROLE_END:
				if (nowState == LoginState.CREATE_ROLE_ING) {
					context.attr(AttributeKey.valueOf(LOGIN_STATE)).set(state);
					return true;
				}
			case SELECT_ROLE_ING:
				if (nowState == LoginState.LOGIN_END || nowState == LoginState.CREATE_ROLE_END) {
					context.attr(AttributeKey.valueOf(LOGIN_STATE)).set(state);
					return true;
				}
			case SELECT_ROLE_END:
				if (nowState == LoginState.SELECT_ROLE_ING) {
					context.attr(AttributeKey.valueOf(LOGIN_STATE)).set(state);
					return true;
				}
			}
			return false;
		}
	}

	public boolean check(ChannelHandlerContext context, LoginState state) {
		synchronized (context) {
			return state == (LoginState)context.attr(AttributeKey.valueOf(LOGIN_STATE)).get();
		}
	}

	public static void main(String[] args) {
		Integer tmp = (Integer)null;
		System.out.println(tmp);
	}
}
