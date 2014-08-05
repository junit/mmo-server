package com.game.account.struct;

import java.util.HashMap;

import com.game.role.struct.Role;


public class Account {
	private long id;
	private String name;
	private int server;
	private String platform;
	private long createTime;
	private HashMap<Long, Role> roles = new HashMap<>();
	private Role role; // 当前游戏的角色
	private boolean online = true; // 在线标志
	
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public HashMap<Long, Role> getRoles() {
		return roles;
	}
	public void setRoles(HashMap<Long, Role> roles) {
		this.roles = roles;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getServer() {
		return server;
	}
	public void setServer(int server) {
		this.server = server;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
