package com.game.role.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import com.db.data.bean.RoleBean;
import com.db.data.dao.RoleDao;
import com.game.role.struct.Role;
import com.manager.ManagerPool;

public class RoleCache {
	private ConcurrentHashMap<Long, ConcurrentHashMap<Long, Role>> roles = new ConcurrentHashMap<>();

	public List<Role> getRoleByAccountId(long accountId) {
		ConcurrentHashMap<Long, Role> map = roles.get(accountId);
		if (map == null) {
			return null;
		}
		return new ArrayList<Role>(map.values());
	}

	public List<Role> getRoleByAccountIdFromDb(long accountId) {
		List<RoleBean> beans = RoleDao.getInstance().selectByAccountId(accountId);
		
		if (beans == null || beans.isEmpty()) {
			return null;
		}
		ArrayList<Role> roles = new ArrayList<Role>();
		for (RoleBean bean : beans) {
			roles.add(ManagerPool.role.createRole(bean));
		}
		return roles;
	}

	public void add(Role role) {
		ConcurrentHashMap<Long, Role> map = roles.get(role.getAccountId());
		if (map == null) {
			map = new ConcurrentHashMap<Long, Role>();
			roles.put(role.getAccountId(), map);
		}
		map.put(role.getId(), role);
	}

}
