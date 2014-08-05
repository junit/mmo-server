package com.game.manager;

import java.util.HashSet;

import org.apache.log4j.Logger;

import com.game.config.ConfigManager;
import com.game.message.manager.MessageManager;

public class ManagerPool {
	public static MessageManager message = new MessageManager();
	public static ConfigManager config = new ConfigManager();
	
	public static HashSet<Manager> managers;
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
