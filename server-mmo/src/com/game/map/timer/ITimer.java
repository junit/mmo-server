package com.game.map.timer;

public abstract class ITimer {
	private long lastTime;
	private long interval;
	public ITimer(long interval) {
		this.interval = interval;
	}

	public boolean canExec() {
		long now = System.currentTimeMillis();
		if (now - lastTime < interval) {
			return false;
		}
		lastTime = now;
		return true;
	}

	public abstract void exec();

}
