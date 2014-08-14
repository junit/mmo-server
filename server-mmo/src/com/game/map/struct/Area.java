package com.game.map.struct;

import java.util.HashMap;

import com.game.role.struct.Role;

public class Area {
	private HashMap<Long, Role> roles = new HashMap<>();

	public HashMap<Long, Role> getRoles() {
		return roles;
	}

	public void setRoles(HashMap<Long, Role> roles) {
		this.roles = roles;
	}
}
