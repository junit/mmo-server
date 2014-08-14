package com.db.config;

import com.db.config.container.MapContainer;
import com.manager.Manager;
import com.manager.PriorityEnum;

public class DbConfigManager extends Manager {
	public MapContainer map = new MapContainer();
	
	@Override
	public boolean init() {
		if (!map.init()) return false;
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
