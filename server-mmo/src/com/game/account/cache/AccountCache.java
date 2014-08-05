package com.game.account.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.db.data.bean.AccountBean;
import com.db.data.dao.AccountDao;
import com.game.account.struct.Account;
import com.manager.ManagerPool;


public class AccountCache {
	private static Logger logger = Logger.getLogger(AccountCache.class);
	private ConcurrentHashMap<Key, Account> accounts = new ConcurrentHashMap<>();
	private AccountDao dao = new AccountDao();
	
	/**
	 * 从内存中获取玩家数据
	 * @param platform 平台
	 * @param server 服务器
	 * @param accountName 账号
	 * @return
	 */
	public Account get(String platform, int server, String accountName) {
		return accounts.get(new Key(platform, server, accountName));
	}

	/**
	 * 从数据库中获取玩家数据
	 * @param platform 平台
	 * @param server 服务器
	 * @param accountName 账号
	 * @return
	 */
	public Account getFromDb(String platform, int server, String accountName) {
		AccountBean bean = dao.select(platform, server, accountName);
		if (bean == null) {
			return null;
		}
		Account account = ManagerPool.account.createAccount(bean);
		return account;
	}

	public void add(Account account) {
		accounts.put(new Key(account.getPlatform(), account.getServer(), account.getName()), account);
		logger.error("当前在线玩家数量:" + accounts.size());
	}
}
