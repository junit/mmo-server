package com.game.role.manager;

import java.util.List;

import org.apache.log4j.Logger;

import com.db.data.bean.RoleBean;
import com.game.account.struct.Account;
import com.game.role.cache.RoleCache;
import com.game.role.struct.Role;
import com.game.role.struct.RoleInsertThread;
import com.game.role.struct.RoleSaveData;
import com.game.role.struct.RoleSaveData.Role.Builder;
import com.game.util.IdGenerator;
import com.google.protobuf.InvalidProtocolBufferException;
import com.manager.Manager;
import com.manager.ManagerPool;

public class RoleManager extends Manager {
	private static Logger logger = Logger.getLogger(RoleManager.class);
	private RoleCache cache = new RoleCache();
	
	@Override
	public boolean init() {
		return true;
	}

	@Override
	public void stop() {
	}

	/**
	 * 获取角色列表
	 * @param accountId 账号id
	 * @return
	 */
	public List<Role> getRoleByAccountId(long accountId) {
		List<Role> roles = cache.getRoleByAccountId(accountId);
		if (roles == null) {
			roles = cache.getRoleByAccountIdFromDb(accountId);
			if (roles != null) {
				for (Role role : roles) {
					cache.add(role);
				}
			}
		}
		
		return roles;
	}

	public Role createRole(Account account, String name) {
		String roleName = generateName(account, name);
		if (!ManagerPool.name.isLegal(name)) {
			return null;
		}
		
		if (ManagerPool.name.isRepeat(roleName, true)) {
			return null;
		}
		
		Role role = new Role();
		role.setId(IdGenerator.getId(account.getServer()));
		role.setCreateTime(System.currentTimeMillis());
		role.setAccountId(account.getId());
		role.setName(roleName);
		account.getRoles().put(role.getId(), role);
		cache.add(role);
		insert(role);
		return role;
	}

	private void insert(Role role) {
		ManagerPool.thread.getDbExcutor().execute(new RoleInsertThread(createBean(role)));
	}

	private RoleBean createBean(Role role) {
		Builder builder = RoleSaveData.Role.newBuilder();
		builder.setId(role.getId());
		builder.setAccountId(role.getAccountId());
		builder.setName(role.getName());
		builder.setCreateTime(role.getCreateTime());
		return createBean(builder);
	}

	private RoleBean createBean(Builder builder) {
		RoleBean bean = new RoleBean();
		bean.setId(builder.getId());
		bean.setAccount(builder.getAccountId());
		bean.setCreateTime(builder.getCreateTime());
		bean.setName(builder.getName());
		bean.setData(builder.build().toByteArray());
		return bean;
	}

	private String generateName(Account account, String name) {
		return new StringBuilder().append(account.getServer()).append(".").append(name).toString();
	}

	public Role createRole(RoleBean bean) {
		try {
			com.game.role.struct.RoleSaveData.Role roleData = RoleSaveData.Role.parseFrom(bean.getData());
			return createRole(roleData); 
		} catch (InvalidProtocolBufferException e) {
			logger.error(e, e);
			return null;
		}
	}

	private Role createRole(com.game.role.struct.RoleSaveData.Role roleData) {
		Role role = new Role();
		role.setId(roleData.getId());
		role.setAccountId(roleData.getAccountId());
		role.setName(roleData.getName());
		role.setCreateTime(roleData.getCreateTime());
		return role;
	}

}
