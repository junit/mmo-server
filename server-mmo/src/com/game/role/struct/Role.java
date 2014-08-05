package com.game.role.struct;

import com.game.login.message.RoleBrief;
import com.game.map.struct.RoleMapData;

public class Role {
	private long id;
	private String name;
	private long accountId;
	private long createTime;
	private RoleMapData map;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public RoleBrief getBriefInfo() {
		RoleBrief brief = new RoleBrief();
		brief.setRoleId(id);
		brief.setName(name);
		return brief;
	}

	public long getAccountId() {
		return accountId;
	}

	public void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public RoleMapData getMap() {
		return map;
	}

	public void setMap(RoleMapData map) {
		this.map = map;
	}
}
