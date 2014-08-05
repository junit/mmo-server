package com.db.config.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.db.config.dao.${name?cap_first}Dao;
import com.db.config.bean.${name?cap_first}Bean;

public class ${name?cap_first}Container {
	private List<${name?cap_first}Bean> list = new ArrayList<>();
	private Map<Integer, ${name?cap_first}Bean> map = new HashMap<>();
	private ${name?cap_first}Dao dao = new ${name?cap_first}Dao();
	public List<${name?cap_first}Bean> getList() {
		return list;
	}
	public Map<Integer, ${name?cap_first}Bean> getMap() {
		return map;
	}
	public boolean init() {
		list = dao.select();
		for (${name?cap_first}Bean bean : list) {
			map.put(bean.getId(), bean);
		}
		return true;
	}
}
