package com.game.map.thread;

import java.util.concurrent.ConcurrentHashMap;

public class MapThreadPool {
	private ConcurrentHashMap<Long, MapThread> threads = new ConcurrentHashMap<>();

	public ConcurrentHashMap<Long, MapThread> getThreads() {
		return threads;
	}

	public void setThreads(ConcurrentHashMap<Long, MapThread> threads) {
		this.threads = threads;
	}
}
