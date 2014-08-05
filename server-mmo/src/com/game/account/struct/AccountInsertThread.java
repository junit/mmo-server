package com.game.account.struct;

import com.db.data.bean.AccountBean;
import com.db.data.dao.AccountDao;

public class AccountInsertThread implements Runnable {
	private AccountBean bean;
	private static AccountDao dao = new AccountDao();
	public AccountInsertThread(AccountBean bean) {
		this.bean = bean;
	}

	@Override
	public void run() {
		dao.insert(bean);
	}

}
