package com.script.manager;

import java.util.HashMap;

import com.game.script.ScriptLoader;
import com.game.script.struct.IScript;
import com.manager.Manager;


public class ScriptManager extends Manager {
	private boolean flag = false;
	@Override
	public boolean init() {
		if (!flag) {
			return true;
		}
		if (System.getProperty("os.name").startsWith("Win")) {
			logger.error("windows 不加载脚本");
			return true;
		}
		try {
			scripts = loader.loadJar("script.jar");
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
		return true;
	}
	
	public IScript getScript(int id) {
		return scripts.get(id);
	}
	
	public boolean reload(int id) {
		try {
			HashMap<Integer, IScript> tmp = loader.loadJar("script.jar");
			IScript script = tmp.get(id);
			scripts.put(script.getId(), script);
		} catch (Exception e) {
			logger.error(e, e);
			return false;
		}
		return true;
	}

	private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
	private ScriptLoader loader = new ScriptLoader();
	private HashMap<Integer, IScript> scripts;
	@Override
	public void stop() {
	}
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("os.name"));
	}
}
