package com.game.account.cache;

public class Key {
	private String platform; // 平台
	private int server; // 服务器
	private String accountName; // 账号
	public Key(String platform, int server, String accountName) {
		this.platform = platform;
		this.server = server;
		this.accountName = accountName;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof Key)) {
			return false;
		}
		Key key = (Key)obj;
		if (this.platform.equals(key.platform) && this.server == key.server && this.accountName.equals(key.accountName)) {
			return true;
		}
		return false;
    }
	
	public int hashCode() {
		StringBuilder builder = new StringBuilder();
		return builder.append(platform).append(String.valueOf(server)).append(accountName).toString().hashCode();
	}
}
