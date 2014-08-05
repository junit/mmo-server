package com.game.map.struct;

import com.game.message.struct.Handler;
import com.game.thread.queue.ITask;

public class MapTask implements ITask {
	private Handler handler;
	
	public MapTask(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void exec() {
		handler.exec();
	}

}
