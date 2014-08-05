package com.game.thread.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 保证定量的任务不被拒绝的一个executor
 * 
 * @author shell
 *
 */
public class FixedPoolExecutor {
	private ThreadPoolExecutor excutor;
	private int maxTask;

	public FixedPoolExecutor(int threadNum, int maxTask) {
		excutor = new ThreadPoolExecutor(threadNum, threadNum, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.DiscardPolicy());
		this.maxTask = maxTask;
	}

	public boolean execute(Runnable task) {
		if (excutor.getQueue().size() >= maxTask) {
			return false;
		}
		excutor.execute(task);
		return true;
	}
}
