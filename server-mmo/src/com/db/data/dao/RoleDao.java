package com.db.data.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.db.DbFactory;
import com.db.data.bean.RoleBean;

public class RoleDao {
	private RoleDao() {}
	private static RoleDao instance = new RoleDao();
	public static RoleDao getInstance() {
		return instance;
	}
	SqlSessionFactory factory = DbFactory.getInstance().getDataFactory();

	public List<RoleBean> select() {
		long s = System.currentTimeMillis();
		SqlSession session = factory.openSession();
		try {
			List<RoleBean> list = session.selectList("role.select");
			long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.GlobalLogger.db.error(new StringBuilder().append("RoleDao.").append("select:").append(interval));
			}
			return list;
		} finally {
			session.close();
		}
	}

	public void insert(RoleBean bean) {
		long s = System.currentTimeMillis();
		SqlSession session = factory.openSession();
		try {
			session.insert("role.insert", bean);
			session.commit();
			long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.GlobalLogger.db.error(new StringBuilder().append("RoleDao.").append("insert:").append(interval));
			}
		} catch (Exception e) {
			com.logger.GlobalLogger.db.error(e, e);
		} finally {
			session.close();
		}
	}

	public List<RoleBean> selectByAccountId(long accountId) {
		long s = System.currentTimeMillis();
		SqlSession session = factory.openSession();
		try {
			List<RoleBean> list = session.selectList("role.selectByAccountId", accountId);
			long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.GlobalLogger.db.error(new StringBuilder().append("RoleDao.").append("selectByAccountId:").append(interval));
			}
			return list;
		} finally {
			session.close();
		}
	}

	public List<String> selectNames() {
		long s = System.currentTimeMillis();
		SqlSession session = factory.openSession();
		try {
			List<String> list = session.selectList("role.selectNames");
			long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.GlobalLogger.db.error(new StringBuilder().append("RoleDao.").append("selectNames:").append(interval));
			}
			return list;
		} finally {
			session.close();
		}
	}

	public void update(RoleBean bean) {
		long s = System.currentTimeMillis();
		SqlSession session = factory.openSession();
		try {
			session.update("role.update", bean);
			session.commit();
			long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.GlobalLogger.db.error(new StringBuilder().append("RoleDao.").append("update:").append(interval));
			}
		} finally {
			session.close();
		}
	}
}