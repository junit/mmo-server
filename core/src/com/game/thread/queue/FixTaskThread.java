package com.game.thread.queue;

import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

public class FixTaskThread extends Thread {
	private Logger logger;
	private LinkedBlockingQueue<ITask> tasks;
	private boolean stopFlag = false;

	public FixTaskThread(String name, int size) {
		super(name);
		tasks = new LinkedBlockingQueue<>(size);
		logger = Logger.getLogger(name);
	}

	@Override
	public void run() {
		while (!stopFlag) {
			ITask task = tasks.poll();
			if (task == null) {
				try {
					synchronized (this) {
						wait();
					}
				} catch (Exception e) {
					logger.error(e, e);
				}
			} else {
				long s = System.currentTimeMillis();
				task.exec();
				long interval = System.currentTimeMillis() - s;
				if (s > 10) {
					logger.error(task.getClass().getName() + ":" + interval);
				}
			}
		}
	}

	public void addTask(ITask task) {
		try {
			tasks.add(task);
			synchronized (this) {
				notify();
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
	public void onStop() {
		logger.error(getName() + "关闭..." + tasks.size());
		stopFlag = true;
		for (ITask task = tasks.poll(); task != null; task = tasks.poll()) {
			long s = System.currentTimeMillis();
			task.exec();
			long interval = System.currentTimeMillis() - s;
			if (s > 10) {
				logger.error(task.getClass().getName() + ":" + interval);
			}
		}
		logger.error(getName() + "关闭");
	}

	public static void main(String[] args) throws Exception {
		FixTaskThread thread = new FixTaskThread("shell", 10);
		thread.start();
	}
}
