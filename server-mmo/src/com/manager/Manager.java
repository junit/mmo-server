package com.manager;

public abstract class Manager {
	protected Manager() {
		ManagerPool.regist(this);
	}
	
	public abstract boolean init();
	public abstract void stop();
}
