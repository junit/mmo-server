package com.game.role.struct;

import com.db.data.bean.RoleBean;
import com.db.data.dao.RoleDao;

public class RoleInsertThread implements Runnable {
	private static RoleDao dao = new RoleDao();
	private RoleBean bean;
	public RoleInsertThread(RoleBean bean) {
		this.bean = bean;
	}

	@Override
	public void run() {
		dao.insert(bean);
	}

}
