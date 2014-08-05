package com.db.config.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.db.DbFactory;
import com.db.config.bean.MapBean;

public class MapDao {
	SqlSessionFactory factory = DbFactory.getInstance().getConfigFactory();

	public List<MapBean> select() {
		long s = System.currentTimeMillis();
        SqlSession session = factory.openSession();
        try{
        	List<MapBean> list = session.selectList("map.select");
        	long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.DbLogger.logger.error(new StringBuilder().append("MapDao.").append("select:").append(interval));
			}
            return list;
    	}finally{
			session.close();
		}
    }
    
    public void insert(MapBean bean) {
		long s = System.currentTimeMillis();
        SqlSession session = factory.openSession();
        try{
        	session.insert("map.insert", bean);
        	long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.DbLogger.logger.error(new StringBuilder().append("MapDao.").append("select:").append(interval));
			}
    	}finally{
			session.close();
		}
	}
}