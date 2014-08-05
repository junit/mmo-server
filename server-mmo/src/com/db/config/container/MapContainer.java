package com.db.config.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.db.config.dao.MapDao;
import com.db.config.bean.MapBean;

public class MapContainer {
	private List<MapBean> list = new ArrayList<>();
	private Map<Integer, MapBean> map = new HashMap<>();
	private MapDao dao = new MapDao();
	public List<MapBean> getList() {
		return list;
	}
	public Map<Integer, MapBean> getMap() {
		return map;
	}
	public boolean init() {
		list = dao.select();
		for (MapBean bean : list) {
			map.put(bean.getId(), bean);
		}
		return true;
	}
}
