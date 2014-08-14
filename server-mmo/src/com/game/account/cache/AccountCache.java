package com.game.account.cache;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.db.data.bean.AccountBean;
import com.db.data.dao.AccountDao;
import com.game.account.struct.Account;
import com.game.role.struct.Role;
import com.manager.ManagerPool;


public class AccountCache {
	public final int MAX=10000;
	private static Logger logger = Logger.getLogger(AccountCache.class);
	private ConcurrentHashMap<Key, Account> accounts = new ConcurrentHashMap<>();
	
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
		AccountBean bean = AccountDao.getInstance().select(platform, server, accountName);
		if (bean == null) {
			return null;
		}
		Account account = ManagerPool.account.createAccount(bean);
		return account;
	}

	public void add(Account account) {
		accounts.put(getKey(account), account);
		if (accounts.size() > MAX) {
			clean();
		}
	}
	
	private Key getKey(Account account) {
		return new Key(account.getPlatform(), account.getServer(), account.getName());
	}

	private void clean() {
		logger.error("清理开始:" + accounts.size());
		for (Object obj : accounts.values().toArray()) {
			Account account = (Account)obj;
			if (System.currentTimeMillis() - account.getOffLineTime() < 5 * 60 * 1000) {
				continue;
			}
			
			accounts.remove(getKey(account));
			ManagerPool.account.update(account);
		}
		logger.error("清理结束:" + accounts.size());
	}

	public void saveAll() {
		for (Object object : accounts.values().toArray()) {
			Account account = (Account)object;
			ManagerPool.account.update(account);
			
			for (Object object2 : account.getRoles().values().toArray()) {
				Role role = (Role)object2;
				ManagerPool.role.update(role);
			}
		}
	}
}
