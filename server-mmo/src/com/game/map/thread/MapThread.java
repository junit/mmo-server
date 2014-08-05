package com.game.map.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.game.map.timer.ITimer;
import com.game.message.struct.Handler;
import com.game.thread.queue.FixTaskThread;

public class MapThread extends Thread {
	private Logger logger;
	private LinkedBlockingQueue<Handler> tasks = new LinkedBlockingQueue<>();
	private List<ITimer> timers = new ArrayList<>();

	public MapThread(String name) {
		super(name);
		logger = Logger.getLogger(name);
	}

	@Override
	public void run() {
		while (true) {
			Handler handler = tasks.poll();
			if (handler == null) {
				try {
					synchronized (this) {
						wait();
					}
				} catch (Exception e) {
					logger.error(e, e);
				}
			} else {
				long s = System.currentTimeMillis();
				handler.exec();
				long interval = System.currentTimeMillis() - s;
				if (s > 10) {
					logger.error(handler.getClass().getName() + ":" + interval);
				}
			}
			
			for (ITimer timer : timers) {
				if (!timer.canExec()) {
					continue;
				}
				timer.exec();
			}
		}
	}

	public void addTask(Handler handler) {
		try {
			tasks.add(handler);
			synchronized (this) {
				notify();
			}
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	
	public void addTimer(ITimer timer) {
		timers.add(timer);
	}

	public static void main(String[] args) throws Exception {
		FixTaskThread thread = new FixTaskThread("shell", 10);
		thread.start();
	}
}
