package tool.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tool.util.Config;

public class DbOpt {
	private static DbOpt instance;

	public static DbOpt getInstance() throws Exception {
		if (instance == null) {
			instance = new DbOpt();
		}
		return instance;
	}

	private Connection config;
	private Connection data;

	private DbOpt() throws Exception {
		data = init(Config.getInstance().getData_url(), Config.getInstance().getData_usr(), Config.getInstance().getData_pwd());
		config = init(Config.getInstance().getConfig_url(), Config.getInstance().getConfig_usr(), Config.getInstance().getConfig_pwd());
	}

	private Connection init(String url, String user, String password) {
		Connection conn;
		// 驱动程序名
		String driver = "com.mysql.jdbc.Driver";
		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			conn = DriverManager.getConnection(url, user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	public Object[] getDataTables() throws Exception {
		String sql = "show tables";
		ResultSet tables = data.getMetaData().getTables(null, null, null, new String[] { "TABLE" });
		List<String> list = new ArrayList<>();

		while (tables.next()) {
			list.add(tables.getString(3));
		}

		return list.toArray();
	}

	public Object[] getConfigTables() throws Exception {
		String sql = "show tables";
		ResultSet tables = config.getMetaData().getTables(null, null, null, new String[] { "TABLE" });
		List<String> list = new ArrayList<>();

		while (tables.next()) {
			list.add(tables.getString(3));
		}

		return list.toArray();
	}

	public Map<String, String> getDataTableMap(String table) throws Exception {
		HashMap<String, String> map = new HashMap<>();
		String sql = new StringBuilder().append("select * from ").append(table).toString();
		Statement statement = data.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int colcount = rsmd.getColumnCount();// 取得全部列数
		for (int i = 1; i <= colcount; i++) {
			map.put(rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
		}
		return map;
	}

	public Map<String, String> getConfigTableMap(String table) throws Exception {
		HashMap<String, String> map = new HashMap<>();
		String sql = new StringBuilder().append("select * from ").append(table).toString();
		Statement statement = config.createStatement();
		ResultSet rs = statement.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int colcount = rsmd.getColumnCount();// 取得全部列数
		for (int i = 1; i <= colcount; i++) {
			map.put(rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
		}
		return map;
	}
}
