package com.db.${type}.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.db.DbFactory;
import com.db.${type}.bean.${name?cap_first}Bean;

public class ${name?cap_first}Dao {
	private ${name?cap_first}Dao() {}
	private static ${name?cap_first}Dao instance = new ${name?cap_first}Dao();
	public static ${name?cap_first}Dao getInstance() {
		return instance;
	}
	SqlSessionFactory factory = DbFactory.getInstance().get${type?cap_first}Factory();

	public List<${name?cap_first}Bean> select() {
		long s = System.currentTimeMillis();
        SqlSession session = factory.openSession();
        try{
        	List<${name?cap_first}Bean> list = session.selectList("${name}.select");
        	long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.DbLogger.logger.error(new StringBuilder().append("${name?cap_first}Dao.").append("select:").append(interval));
			}
            return list;
    	}finally{
			session.close();
		}
    }
    
    public void insert(${name?cap_first}Bean bean) {
		long s = System.currentTimeMillis();
        SqlSession session = factory.openSession();
        try{
        	session.insert("${name}.insert", bean);
        	session.commit();
        	long interval = System.currentTimeMillis() - s;
			if (interval > 10) {
				com.logger.DbLogger.logger.error(new StringBuilder().append("${name?cap_first}Dao.").append("select:").append(interval));
			}
    	}finally{
			session.close();
		}
	}
}