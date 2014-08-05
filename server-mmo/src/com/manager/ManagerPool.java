package com.manager;

import java.util.HashSet;

import org.apache.log4j.Logger;

import com.config.manager.ConfigManager;
import com.db.config.DbConfigManager;
import com.game.account.manager.AccountManager;
import com.game.login.manager.LoginManager;
import com.game.login.manager.LoginStateManager;
import com.game.map.manager.MapManager;
import com.game.name.manager.NameManager;
import com.game.role.manager.RoleManager;
import com.message.manager.MessageManager;
import com.thread.manager.ThreadManager;

public class ManagerPool {
//	public static ScriptManager script = new ScriptManager();
	public static LoginManager login = new LoginManager();
	public static AccountManager account = new AccountManager();
	public static RoleManager role = new RoleManager();
	public static LoginStateManager loginState = new LoginStateManager();
	public static MessageManager message = new MessageManager();
	public static ConfigManager config = new ConfigManager();
	public static ThreadManager thread = new ThreadManager();
	public static MapManager map = new MapManager();
	public static DbConfigManager dbConfig = new DbConfigManager();
	public static NameManager name = new NameManager();
	
	private static HashSet<Manager> managers;
	private static Logger logger = Logger.getLogger(ManagerPool.class);
	public static boolean init() {
		if (managers == null) {
			return true;
		}
		for (Manager manager : managers) {
			if (!manager.init()) {
				logger.error(manager.getClass().getName() + "初始化失败");
				return false;
			}
			logger.error(manager.getClass().getName() + "初始化完成");
		}
		return true;
	}
	
	public static void regist(Manager manager) {
		if (managers == null) {
			managers = new HashSet<>();
		}
		managers.add(manager);
	}
	
	public static void stop() {
		for (Manager manager : managers) {
			manager.stop();
		}
	}
	
	public static void main(String[] args) {
		ManagerPool.init();
	}
}
