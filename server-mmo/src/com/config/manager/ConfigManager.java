package com.config.manager;

import com.config.struct.GameConfig;
import com.config.struct.ServerConfig;
import com.manager.Manager;
import com.manager.PriorityEnum;

public class ConfigManager extends Manager {
	private GameConfig gameCofnig = new GameConfig();
	private ServerConfig serverConfig = new ServerConfig();
	
	public GameConfig getGameCofnig() {
		return gameCofnig;
	}

	public void setGameCofnig(GameConfig gameCofnig) {
		this.gameCofnig = gameCofnig;
	}

	public ServerConfig getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(ServerConfig serverConfig) {
		this.serverConfig = serverConfig;
	}

	@Override
	public boolean init() {
		if (!gameCofnig.init()) {
			return false;
		}
		
		if (!serverConfig.init()) {
			return false;
		}
		
		return true;
	}

	@Override
	public void stop() {
	}
	
	@Override
	public PriorityEnum getPriority() {
		return PriorityEnum.BASE;
	}
}
