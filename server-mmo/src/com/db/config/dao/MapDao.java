package com.db.config.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.db.DbFactory;
import com.db.config.bean.MapBean;

public class MapDao {
	SqlSessionFactory factory = DbFactory.getInstance().getConfigFactory();

	public List<MapBean> select() {
		SqlSession session = factory.openSession();
		try {
			long s = System.currentTimeMillis();
			List<MapBean> list = session.selectList("map.select");
			long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.GlobalLogger.db.error(new StringBuilder().append("MapDao.").append("select:").append(interval));
			}
			return list;
		} finally {
			session.close();
		}
	}

	public void insert(MapBean bean) {
		long s = System.currentTimeMillis();
		SqlSession session = factory.openSession();
		try {
			session.insert("map.insert", bean);
			long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.GlobalLogger.db.error(new StringBuilder().append("MapDao.").append("select:").append(interval));
			}
		} finally {
			session.close();
		}
	}

	public static void main(String[] args) throws Exception {
		Connection con = DriverManager.getConnection("jdbc:mysql://192.168.1.120:3307/shell_config", "game", "game");
		long s = System.currentTimeMillis();
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select maxLine,defaultLine,name,width,id,height from map");
		System.out.println(System.currentTimeMillis() - s);
//		while (rs.next()) {
//			int x = rs.getInt("a");
//			String s = rs.getString("b");
//			float f = rs.getFloat("c");
//		}
	}
}