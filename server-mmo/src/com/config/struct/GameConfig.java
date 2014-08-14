package com.config.struct;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.game.util.TimeUtil;

class Server {
	private int id;
	private long opendate;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getOpendate() {
		return opendate;
	}
	public void setOpendate(long opendate) {
		this.opendate = opendate;
	}
	
}

public class GameConfig {
	private static Logger logger = Logger.getLogger(GameConfig.class);
	private HashMap<String, String> platfom2protocol;
	private HashMap<Integer, Server> servers ;

	/**
	 * 根据平台获取验证key
	 * @param platform
	 * @return
	 */
	public String getProtocol(String platform) {
		return platfom2protocol.get(platform);
	}
	
	/**
	 * 根据服务器id获取服务器的开服时间
	 * @param server
	 * @return
	 */
	public long getOpenDate(int server) {
		return servers.get(server).getOpendate();
	}

	public boolean init() {
		try {
			SAXReader reader = new SAXReader();
			Document doc;
			doc = reader.read(new File("config/game.xml"));
			Element root = doc.getRootElement();
			for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
				Element element = (Element)i.next();
				if (element.getName().equals("servers")) {
					initServers(element);
				} else if (element.getName().equals("protocols")) {
					initProtocols(element);
				}
			}
		} catch (DocumentException e) {
			logger.error(e, e);
			return false;
		}
		return true;
	}

	private void initProtocols(Element root) {
		HashMap<String,String> map = new HashMap<>();
		
		for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element)i.next();
			if (!element.getName().equals("protocol")) {
				continue;
			}
			map.put(element.attributeValue("platfom"), element.attributeValue("key"));
		}
		
		platfom2protocol = map;
	}

	private void initServers(Element root) {
		HashMap<Integer, Server> map = new HashMap<>();
		
		for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element)i.next();
			if (!element.getName().equals("server")) {
				continue;
			}
			Server server = new Server();
			server.setId(Integer.parseInt(element.attributeValue("id")));
			server.setOpendate(TimeUtil.getDateByString(element.attributeValue("opendate")).getTime());
			
			if (server.getId() >= 0xffff) {
				logger.error("服务器id配置错误,不能大于0xffff");
			}
			
			map.put(server.getId(), server);
		}
		
		servers = map;
	}

	public Collection<Integer> getServers() {
		return servers.keySet();
	}
}
