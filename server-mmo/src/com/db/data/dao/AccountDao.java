package com.db.data.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.db.DbFactory;
import com.db.data.bean.AccountBean;

public class AccountDao {
	SqlSessionFactory factory = DbFactory.getInstance().getDataFactory();

	public List<AccountBean> select() {
		long s = System.currentTimeMillis();
        SqlSession session = factory.openSession();
        try{
        	List<AccountBean> list = session.selectList("account.select");
        	long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.DbLogger.logger.error(new StringBuilder().append("AccountDao.").append("select:").append(interval));
			}
            return list;
    	}finally{
			session.close();
		}
    }
    
    public void insert(AccountBean bean) {
		long s = System.currentTimeMillis();
        SqlSession session = factory.openSession();
        try{
        	session.insert("account.insert", bean);
        	long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.DbLogger.logger.error(new StringBuilder().append("AccountDao.").append("select:").append(interval));
			}
    	}finally{
			session.close();
		}
	}

	public AccountBean select(String platform, int server, String name) {
		long s = System.currentTimeMillis();
        SqlSession session = factory.openSession();
        try{
        	HashMap<String, Object> map = new HashMap<>();
        	map.put("platform", platform);
        	map.put("server", server);
        	map.put("name", name);
        	AccountBean bean = session.selectOne("account.selectOne", map);
        	long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.DbLogger.logger.error(new StringBuilder().append("AccountDao.").append("selectOne:").append(interval));
			}
            return bean;
    	}finally{
			session.close();
		}
	}
}