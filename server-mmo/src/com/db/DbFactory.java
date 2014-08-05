package com.db;

import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class DbFactory {
	private static Logger logger = Logger.getLogger(DbFactory.class);
	private static DbFactory instance = new DbFactory();
	private DbFactory() {
		try {
			InputStream in = new FileInputStream("config/db-data.xml");
			dataFactory = new SqlSessionFactoryBuilder().build(in);
			in.close();
		} catch (Exception e) {
			logger.error(e, e);
		}
		
		try {
			InputStream in = new FileInputStream("config/db-config.xml");
			configFactory = new SqlSessionFactoryBuilder().build(in);
			in.close();
		} catch (Exception e) {
			logger.error(e, e);
		}
	}
	public static DbFactory getInstance() {
		return instance;
	}

	private SqlSessionFactory dataFactory;
	private SqlSessionFactory configFactory;
	public SqlSessionFactory getDataFactory() {
		return dataFactory;
	}
	public SqlSessionFactory getConfigFactory() {
		return configFactory;
	}
}
