package com.game.config;

import java.io.File;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.game.manager.Manager;

public class ConfigManager extends Manager {
	private static Logger logger = Logger.getLogger(ConfigManager.class);
	private int robot;
	private String ip;
	private int port;

	public int getRobot() {
		return robot;
	}

	public void setRobot(int robot) {
		this.robot = robot;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	@Override
	public boolean init() {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(new File("config/config.xml"));
		} catch (DocumentException e) {
			logger.error(e, e);
		}
		Element root = doc.getRootElement();
		robot = Integer.parseInt(root.attributeValue("robot"));
		ip = root.attributeValue("ip");
		port = Integer.parseInt(root.attributeValue("port"));
		return true;
	}

	@Override
	public void stop() {
	}

}
