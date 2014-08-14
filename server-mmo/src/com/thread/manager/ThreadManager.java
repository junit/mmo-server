package com.thread.manager;

import com.game.map.thread.MapThreadPool;
import com.game.thread.pool.FixedPoolExecutor;
import com.game.thread.queue.FixTaskThread;
import com.manager.Manager;
import com.manager.PriorityEnum;

public class ThreadManager extends Manager {
	private FixedPoolExecutor loginExecutor = new FixedPoolExecutor(16, 160); // 这个线程池会涉及到读取数据库
	private FixTaskThread dbThread = new FixTaskThread("dbThread", 10000);
	private MapThreadPool mapThreadPool = new MapThreadPool();
	
	@Override
	public boolean init() {
		dbThread.start();
		return true;
	}
	
	@Override
	public PriorityEnum getPriority() {
		return PriorityEnum.NORMAL;
	}

	@Override
	public void stop() {
		dbThread.onStop();
	}

	public FixedPoolExecutor getLoginExecutor() {
		return loginExecutor;
	}

	public void setLoginExecutor(FixedPoolExecutor loginExecutor) {
		this.loginExecutor = loginExecutor;
	}

	public MapThreadPool getMapThreadPool() {
		return mapThreadPool;
	}

	public void setMapThreadPool(MapThreadPool mapThreadPool) {
		this.mapThreadPool = mapThreadPool;
	}

	public FixTaskThread getDbThread() {
		return dbThread;
	}

	public void setDbThread(FixTaskThread dbThread) {
		this.dbThread = dbThread;
	}
}
