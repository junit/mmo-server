package com.game.util;

import java.util.HashSet;

public class IdGenerator {
	private static int seed = 0;
	/**
	 * 产生唯一id(在server>0xffff 或者 每毫秒产生的id>0xffff的时候会出现重复)
	 * @param server
	 * @return
	 */
	public static synchronized long getId(int server) {
		++seed;
		return (server & 0xFFFF) << 48 | (System.currentTimeMillis() / 1000L & 0xFFFFFFFF) << 16 | seed & 0xFFFF;
	}

	public static void main(String[] args) {
		HashSet<Long> set = new HashSet<>();
		int server = 1;
		for (int i = 0; i < 0xFFFF + 5; ++i) {
			long id = getId(server);
			if (set.contains(id)) {
				System.out.println("重复");
			}
			set.add(id);
		}
	}
}
