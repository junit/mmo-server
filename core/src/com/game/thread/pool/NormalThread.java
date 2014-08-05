package com.game.thread.pool;

import com.game.message.struct.Handler;

public class NormalThread implements Runnable {
	private Handler handler;

	public NormalThread(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void run() {
		handler.exec();
	}
}
