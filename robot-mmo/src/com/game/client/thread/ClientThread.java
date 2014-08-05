package com.game.client.thread;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.game.client.GameClient;

public class ClientThread extends Thread {
	private String ip;
	private int port;
	private static Logger logger = Logger.getLogger(ClientThread.class);
	private static AtomicInteger count = new AtomicInteger();
	private static AtomicInteger count2 = new AtomicInteger();
	public ClientThread(int id, String ip, int port) {
		super(new StringBuilder().append("robot").append(id).toString());
		this.ip = ip;
		this.port = port;
	}

	@Override
	public void run() {
		logger.error("start..." + count.incrementAndGet());
		GameClient client = new GameClient();
		client.init(ip, port);
		logger.error("close..." + count2.incrementAndGet());
	}

}
