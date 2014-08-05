package com.game.account.manager;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;

import com.db.data.bean.AccountBean;
import com.game.account.cache.AccountCache;
import com.game.account.struct.Account;
import com.game.account.struct.AccountInsertThread;
import com.game.util.IdGenerator;
import com.manager.Manager;
import com.manager.ManagerPool;
import com.message.util.MessageUtil;

public class AccountManager extends Manager {
	private AccountCache cache = new AccountCache();

	@Override
	public boolean init() {
		return true;
	}

	@Override
	public void stop() {
		// 1.断开连接
		// 2.保存数据
		// TODO 保存账号
	}

	/**
	 * 验证账号是否合法
	 * @param platform 平台
	 * @param protocol 协议
	 * @return
	 */
	public boolean checkProtocol(String platform, String protocol) {
		String key = ManagerPool.config.getGameCofnig().getProtocol(platform);
		if (key == null) {
			key = ManagerPool.config.getGameCofnig().getProtocol("common");
		}
		return protocol.equals(key);
	}

	/**
	 * 根据用户名来获取账号,数据库和内存都会检索
	 * @param platform 平台
	 * @param server 服务器
	 * @param accountName 账号
	 * @return
	 */
	public Account getAccount(String platform, int server, String accountName) {
		Account account = cache.get(platform, server, accountName);
		if (account == null) {
			account = cache.getFromDb(platform, server, accountName);
			if (account != null) {
				cache.add(account);
			}
		}
		
		return account;
	}

	/**
	 * 创建账号,写入数据库
	 * @param platform 平台
	 * @param server 服务器
	 * @param accountName 账号
	 * @return
	 */
	public Account createAccount(String platform, int server, String accountName) {
		Account account = new Account();
		account.setId(IdGenerator.getId(server));
		account.setName(accountName);
		account.setPlatform(platform);
		account.setServer(server);
		account.setCreateTime(System.currentTimeMillis());
		cache.add(account);
		insert(account);
		return account;
	}

	private void insert(Account account) {
		ManagerPool.thread.getDbExcutor().execute(new AccountInsertThread(createAccountBean(account)));
	}

	private static final String ACCOUNT = "ACCOUNT";
	public void bind(ChannelHandlerContext context, Account account) {
		synchronized (context) {
			context.attr(AttributeKey.valueOf(ACCOUNT)).set(account);
			MessageUtil.add(account.getId(), context);
		}
	}
	
	public Account unbind(ChannelHandlerContext context) {
		synchronized (context) {
			Account account = (Account)context.attr(AttributeKey.valueOf(ACCOUNT)).getAndRemove();
			if (account != null) {
				MessageUtil.remove(account.getId());
			}
			return account;
		}
	}

	public Account getAccount(ChannelHandlerContext context) {
		synchronized (context) {
			Account account = (Account)context.attr(AttributeKey.valueOf(ACCOUNT)).get();
			return account;
		}
	}

	public Account createAccount(AccountBean bean) {
		Account account = new Account();
		account.setId(bean.getId());
		account.setName(bean.getName());
		account.setCreateTime(bean.getCreateTime());
		account.setPlatform(bean.getPlatform());
		account.setServer(bean.getServer());
		return account;
	}
	
	public AccountBean createAccountBean(Account account) {
		AccountBean bean = new AccountBean();
		bean.setId(account.getId());
		bean.setName(account.getName());
		bean.setCreateTime(account.getCreateTime());
		bean.setPlatform(account.getPlatform());
		bean.setServer(account.getServer());
		return bean;
	}

	public void onOffline(Account account2) {
	}
}
