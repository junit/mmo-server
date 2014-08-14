package com.game.map.manager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.db.config.bean.MapBean;
import com.game.account.struct.Account;
import com.game.map.struct.Area;
import com.game.map.struct.Map;
import com.game.map.struct.Position;
import com.game.map.struct.RoleMapData;
import com.game.map.thread.MapThread;
import com.game.role.struct.Role;
import com.manager.Manager;
import com.manager.ManagerPool;
import com.manager.PriorityEnum;

public class MapManager extends Manager {
	private static Logger logger = Logger.getLogger(MapManager.class);
	private final int AREA_WIDTH = 10;
	private final int AREA_HEIGHT = 10;
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
	
	@Override
	public PriorityEnum getPriority() {
		return PriorityEnum.NORMAL;
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
		map.setAreas(new Area[bean.getWidth() / AREA_WIDTH + 1][bean.getHeight() / AREA_HEIGHT + 1]);
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

	public HashSet<Role> getRoundRole(Role role) {
		HashSet<Role> roles = new HashSet<>();
		
		Map map = getMap(role.getMap().getId());
		if (map == null) {
			return roles;
		}
		
		List<Area> areas = getRoundArea(map, role.getMap().getPosition());
		for (Area area : areas) {
			roles.addAll(area.getRoles().values());
		}
		
		return roles;
	}

	private List<Area> getRoundArea(Map map, Position position) {
		List<Area> areas = new ArrayList<>();
		int x = position.getX() / AREA_WIDTH;
		int y = position.getY() / AREA_HEIGHT;

		for (int index_x = x - 1; index_x <= x + 1; ++index_x) {
			for (int index_y = y - 1; index_y <= y + 1; ++index_y) {
				if (index_x < 0 || index_y < 0 || index_x >= map.getAreas().length || index_y >= map.getAreas()[0].length) {
					continue;
				}
				areas.add(map.getAreas()[index_x][index_y]);
			}
		}
		
		return areas;
	}
	
	public Area getArea(Map map, Position position) {
		return map.getAreas()[position.getX() / AREA_WIDTH][position.getY() / AREA_HEIGHT];
	}

	private Map getMap(long id) {
		return maps.get(id);
	}
	
	private Map getMap(int server, int model, int line) {
		return getMap(getMapId(server, model, line));
	}

	public void login(Account account) {
		Map map = selectMap(account.getRole().getMap());
		enterMap(account.getRole(), map, account.getRole().getMap().getPosition());
	}

	private void enterMap(Role role, Map map, Position position) {
		role.getMap().setId(map.getId());
		role.getMap().setLine(map.getLine());
		role.getMap().setModel(map.getModel());
		role.getMap().setPosition(position);
		role.getMap().setServer(map.getServer());
		
		Area area = getArea(map, position);
		area.getRoles().put(role.getId(), role);
		// TODO let round know
	}

	private Map selectMap(RoleMapData mapData) {
		Map map = getMap(mapData.getId());
		if (map == null) {
			MapBean bean = ManagerPool.dbConfig.map.getMap().get(mapData.getModel());
			if (bean == null) {
				logger.error("找不到MapBean:" + mapData.getModel());
				return null;
			}
			
			int line = 1;
			for (; line <= bean.getMaxLine(); ++line) {
				map = getMap(mapData.getServer(), bean.getId(), line);
				if (map == null) {
					break;
				}
			}
			map = createMap(bean, mapData.getServer(), line);
		}
		return map;
	}

}
