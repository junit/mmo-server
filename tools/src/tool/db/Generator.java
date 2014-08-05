package tool.db;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import tool.db.struct.Bean;
import tool.db.struct.Container;
import tool.db.struct.Dao;
import tool.db.struct.Field;
import tool.db.struct.Manager;
import tool.db.struct.Mapper;
import tool.ftl.FtlManager;
import tool.ftl.FunctionEnum;
import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.Config;
import tool.util.StringUtil;

public class Generator {
	private String getJavaType(String dbType) {
		if (dbType.equals("BLOB")) return "byte[]";
		if (dbType.equals("BIGINT")) return "long";
		if (dbType.equals("VARCHAR")) return "String";
		if (dbType.equals("INT")) return "int";
		System.err.println(dbType);
		return "";
	}
	
	private String getDbType(String dbType) {
//		if (dbType.equals("BLOB")) return "byte[]";
//		if (dbType.equals("BIGINT")) return "long";
//		if (dbType.equals("VARCHAR")) return "String";
		if (dbType.equals("INT")) return "INTEGER";
		return dbType;
	}

	public void generateData(String table) throws Exception {
		Map<String, String> map = DbOpt.getInstance().getDataTableMap(table);
		List<IFtl> list = generate(table, "data", map);
		for (IFtl ftl : list) {
			FtlManager.getInstance().generate(FunctionEnum.DB, LanguageEnum.JAVA_SERVER, ftl);
		}
		
		tool.db.struct.Config config = createConfig("data", LanguageEnum.JAVA_SERVER);
		FtlManager.getInstance().generate(FunctionEnum.DB, LanguageEnum.JAVA_SERVER, config);
	}

	private tool.db.struct.Config createConfig(String type, LanguageEnum language) {
		tool.db.struct.Config config = new tool.db.struct.Config();
		config.setType(type);
		if (type.equals("config")) {
			config.setUrl(Config.getInstance().getConfig_url());
			config.setUsr(Config.getInstance().getConfig_usr());
			config.setPwd(Config.getInstance().getConfig_pwd());
		}
		if (type.equals("data")) {
			config.setUrl(Config.getInstance().getData_url());
			config.setUsr(Config.getInstance().getData_usr());
			config.setPwd(Config.getInstance().getData_pwd());
		}
		
		String path = tool.util.Config.getInstance().getPath(language);
		File dir = new File(StringUtil.join(path, File.separator, "com.db." + type + ".mapper").replace(".", File.separator));
		if (!dir.exists()) {
			return config;
		}
		for (File file : dir.listFiles()) {
			if (!file.getName().endsWith(".xml")) {
				continue;
			}
			config.getNames().add(file.getName());
		}
		return config;
	}

	public void generateConfig(String table, LanguageEnum language) throws Exception {
		
		
		Map<String, String> map = DbOpt.getInstance().getConfigTableMap(table);
		List<IFtl> list = generate(table, "config", map);
		for (IFtl ftl : list) {
			FtlManager.getInstance().generate(FunctionEnum.DB, language, ftl);
			if (ftl instanceof Bean) {
				Bean bean = (Bean)ftl;
				
			}
		}
		
		Manager manager = createManager(language);
		manager.setType("config");
		FtlManager.getInstance().generate(FunctionEnum.DB, language, manager);
		
		tool.db.struct.Config config = createConfig("config", language);
		FtlManager.getInstance().generate(FunctionEnum.DB, language, config);
	}

	public void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}
	
	private List<IFtl> generate(String table, String type, Map<String, String> map){
		List<IFtl> list = new ArrayList<>();
		Bean bean = new Bean();
		Dao dao = new Dao();
		Mapper mapper = new Mapper();
		list.add(bean);
		list.add(dao);
		list.add(mapper);
		
		bean.setName(table);
		dao.setName(table);
		mapper.setName(table);
		
		bean.setType(type);
		dao.setType(type);
		mapper.setType(type);
		
		if (type.equals("config")) {
			Container container = new Container();
			list.add(container);
			
			container.setName(table);
			container.setType(type);
		}
		
		for (Iterator<Entry<String, String>> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<String, String> entry = it.next();
			Field field = new Field();
			bean.getFields().add(field);
			mapper.getFields().add(field);
			
			field.setName(entry.getKey());
			field.setDbType(getDbType(entry.getValue()));
			field.setClazz(getJavaType(entry.getValue()));
		}
		return list;
	}

	private Manager createManager(LanguageEnum language) {
		Manager manager = new Manager();
		String path = tool.util.Config.getInstance().getPath(language);
		File dir = new File(StringUtil.join(path, File.separator, "com.db.config.mapper").replace(".", File.separator));
		if (!dir.exists()) {
			return manager;
		}
		for (File file : dir.listFiles()) {
			if (!file.getName().endsWith(".xml")) {
				continue;
			}
			manager.getNames().add(StringUtil.lowFirstChar(file.getName().substring(0, file.getName().length() - 4)));
		}
		return manager;
	}
}
