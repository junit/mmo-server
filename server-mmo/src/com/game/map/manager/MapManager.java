package com.game.map.manager;

import java.util.concurrent.ConcurrentHashMap;

import com.db.config.bean.MapBean;
import com.game.map.struct.Map;
import com.game.map.struct.RoleMapData;
import com.game.map.thread.MapThread;
import com.manager.Manager;
import com.manager.ManagerPool;

public class MapManager extends Manager {
	private ConcurrentHashMap<Long, Map> maps = new ConcurrentHashMap<>();

	@Override
	public boolean init() {
		for (MapBean bean : ManagerPool.dbConfig.map.getList()) {
			if (!init(bean)) {
				return false;
			}
		}
		return true;
	}

	private boolean init(MapBean bean) {
		for (int server : ManagerPool.config.getGameCofnig().getServers()) {
			for (int line = 1; line <= bean.getDefaultLine(); ++line) {
				Map map = createMap(bean, server, line);
				if (map == null) {
					return false;
				}
				maps.put(map.getId(), map);
				ManagerPool.thread.getMapThreadPool().getThreads().put(map.getId(), createMapThread(getMapName(bean.getName(), server, line)));
			}
		}
		return true;
	}

	private MapThread createMapThread(String mapName) {
		MapThread thread = new MapThread(mapName);
		return thread;
	}

	private Map createMap(MapBean bean, int server, int line) {
		Map map = new Map();
		map.setModel(bean.getId());
		map.setServer(server);
		map.setLine(line);
		map.setId(getMapId(map.getServer(), map.getModel(), map.getLine()));
		return map;
	}

	private long getMapId(int server, int model, int line) {
		return (server &0xffff) << 48 | (line & 0xffff) << 32 | model;
	}
	
	private String getMapName(String name, int server, int line) {
		return new StringBuilder().append(name).append("(").append(server).append(",").append(line).append("").toString();
	}

	@Override
	public void stop() {
	}

	public MapThread getMapThread(RoleMapData map) {
		return ManagerPool.thread.getMapThreadPool().getThreads().get(map.getId());
	}

}
