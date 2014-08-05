package com.thread.manager;

import com.game.map.thread.MapThreadPool;
import com.game.thread.pool.FixedPoolExecutor;
import com.manager.Manager;

public class ThreadManager extends Manager {
	private FixedPoolExecutor loginExecutor = new FixedPoolExecutor(16, 160); // 这个线程池会涉及到读取数据库
	private FixedPoolExecutor dbExcutor = new FixedPoolExecutor(16, 1000);
	private MapThreadPool mapThreadPool = new MapThreadPool();
	
	@Override
	public boolean init() {
		return true;
	}

	@Override
	public void stop() {
		// TODO 这里需要处理完所有的保存
	}

	public FixedPoolExecutor getLoginExecutor() {
		return loginExecutor;
	}

	public void setLoginExecutor(FixedPoolExecutor loginExecutor) {
		this.loginExecutor = loginExecutor;
	}

	public FixedPoolExecutor getDbExcutor() {
		return dbExcutor;
	}

	public void setDbExcutor(FixedPoolExecutor dbExcutor) {
		this.dbExcutor = dbExcutor;
	}

	public MapThreadPool getMapThreadPool() {
		return mapThreadPool;
	}

	public void setMapThreadPool(MapThreadPool mapThreadPool) {
		this.mapThreadPool = mapThreadPool;
	}
}
