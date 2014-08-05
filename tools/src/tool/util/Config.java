package tool.util;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import tool.ftl.LanguageEnum;

public class Config {
	private static Config instance = new Config();
	private Config() {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(new File("config/config.xml"));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();

		for (Iterator<?> i = root.elementIterator(); i.hasNext();) {
			Element element = (Element) i.next();
			if (element.getName().equals("java-client")) {
				javaClientPath = element.attributeValue("path");
			} else if (element.getName().equals("java-server")) {
				javaServerPath = element.attributeValue("path");
			} else if (element.getName().equals("message")) {
				messagePath = element.attributeValue("path");
			} else if (element.getName().equals("as")) {
				asPath = element.attributeValue("path");
			} else if (element.getName().equals("db-config")) {
				config_url = element.attributeValue("url");
				config_usr = element.attributeValue("usr");
				config_pwd = element.attributeValue("pwd");
			} else if (element.getName().equals("db-data")) {
				data_url = element.attributeValue("url");
				data_usr = element.attributeValue("usr");
				data_pwd = element.attributeValue("pwd");
			}
		}
	}
	public static Config getInstance() {
		return instance;
	}
	
	private String javaClientPath;
	private String javaServerPath;
	private String messagePath;
	private String asPath;
	
	private String data_url;
	private String data_usr;
	private String data_pwd;
	
	private String config_url;
	private String config_usr;
	private String config_pwd;
	
	public String getPath(LanguageEnum type) {
		switch (type) {
		case AS: return asPath;
		case JAVA_CLIENT: return javaClientPath;
		case JAVA_SERVER: return javaServerPath;
		}
		return null;
	}
	public String getJavaClientPath() {
		return javaClientPath;
	}
	public void setJavaClientPath(String javaClientPath) {
		this.javaClientPath = javaClientPath;
	}
	public String getJavaServerPath() {
		return javaServerPath;
	}
	public void setJavaServerPath(String javaServerPath) {
		this.javaServerPath = javaServerPath;
	}
	public String getMessagePath() {
		return messagePath;
	}
	public void setMessagePath(String messagePath) {
		this.messagePath = messagePath;
	}
	public String getAsPath() {
		return asPath;
	}
	public void setAsPath(String asPath) {
		this.asPath = asPath;
	}
	public String getData_url() {
		return data_url;
	}
	public void setData_url(String data_url) {
		this.data_url = data_url;
	}
	public String getData_usr() {
		return data_usr;
	}
	public void setData_usr(String data_usr) {
		this.data_usr = data_usr;
	}
	public String getData_pwd() {
		return data_pwd;
	}
	public void setData_pwd(String data_pwd) {
		this.data_pwd = data_pwd;
	}
	public String getConfig_url() {
		return config_url;
	}
	public void setConfig_url(String config_url) {
		this.config_url = config_url;
	}
	public String getConfig_usr() {
		return config_usr;
	}
	public void setConfig_usr(String config_usr) {
		this.config_usr = config_usr;
	}
	public String getConfig_pwd() {
		return config_pwd;
	}
	public void setConfig_pwd(String config_pwd) {
		this.config_pwd = config_pwd;
	}
	public static void setInstance(Config instance) {
		Config.instance = instance;
	}
}
