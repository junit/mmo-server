package tool.db.struct;

import java.io.File;
import java.util.HashMap;

import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.Config;
import tool.util.StringUtil;

public class Dao implements IFtl {
	private String type;
	private String name;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public HashMap<String, Object> getDataModel() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("name", name);
		return map;
	}

	@Override
	public String getFtlFileName() {
		return "dao.ftl";
	}

	@Override
	public String getDstPath(LanguageEnum language) {
		String string = StringUtil.join(Config.getInstance().getPath(language), File.separator, "com.db.", type, ".dao.");
		return StringUtil.join(string.replace(".", File.separator), StringUtil.upFirstChar(name), "Dao.java");
	}

	@Override
	public boolean isRewrite() {
		return false;
	}

}
