package com.config.struct;

import java.io.File;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServerConfig {
	public class GameServerConfig {
		private int port;
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
	}
	
	private static Logger logger = Logger.getLogger(ServerConfig.class);
	private GameServerConfig gameServerConfig;

	public boolean init() {
		try {
			SAXReader reader = new SAXReader();
			Document doc;
			doc = reader.read(new File("config/server.xml"));
			Element root = doc.getRootElement();
			for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
				Element element = (Element)i.next();
				if (element.getName().equals("gameserver")) {
					initGameServerConfig(element);
				}
			}
		} catch (DocumentException e) {
			logger.error(e, e);
			return false;
		}
		return true;
	}

	private void initGameServerConfig(Element element) {
		GameServerConfig config = new GameServerConfig();
		config.setPort(Integer.parseInt(element.attributeValue("port")));
		gameServerConfig = config;
	}

	public GameServerConfig getGameServerConfig() {
		return gameServerConfig;
	}
}
