package tool.db.struct;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.Config;
import tool.util.StringUtil;

public class Manager implements IFtl {
	private List<String> names = new ArrayList<>();
	private String type;

	@Override
	public HashMap<String, Object> getDataModel() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("names", names);
		return map;
	}

	@Override
	public String getFtlFileName() {
		return "manager.ftl";
	}

	@Override
	public String getDstPath(LanguageEnum language) {
		String string = StringUtil.join(Config.getInstance().getPath(language), File.separator, "com.db.", type, ".");
		return StringUtil.join(string.replace(".", File.separator), "DbConfigManager.java");
	}

	@Override
	public boolean isRewrite() {
		return true;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
