package com.game.name.manager;

import java.util.HashSet;

import com.db.data.dao.RoleDao;
import com.manager.Manager;
import com.manager.PriorityEnum;

public class NameManager extends Manager {
	private HashSet<String> names = new HashSet<>(); // 角色名称,帮会名称等
	private HashSet<String> illegalStrs = new HashSet<>(); // 非法字符

	@Override
	public boolean init() {
		{
			names.addAll(RoleDao.getInstance().selectNames());
		}
		return true;
	}

	@Override
	public void stop() {
	}
	
	@Override
	public PriorityEnum getPriority() {
		return PriorityEnum.NORMAL;
	}

	public boolean isRepeat(String name, boolean insert) {
		synchronized (names) {
			if (names.contains(name)) {
				return true;
			}
			if (insert) {
				names.add(name);
			}
			return false;
		}
	}

	public boolean isLegal(String str) {
		for (String illegalStr : illegalStrs) {
			if (str.indexOf(illegalStr) != -1) {
				return false;
			}
		}
		return true;
	}
}
