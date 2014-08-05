package tool.db.struct;

import java.io.File;
import java.util.HashMap;

import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.Config;
import tool.util.StringUtil;

public class Container implements IFtl {
	private String name;
	private String type;

	@Override
	public HashMap<String, Object> getDataModel() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("name", name);
		return map;
	}

	@Override
	public String getFtlFileName() {
		return "container.ftl";
	}

	@Override
	public String getDstPath(LanguageEnum language) {
		String string = StringUtil.join(Config.getInstance().getPath(language), File.separator, "com.db.", type, ".container.");
		return StringUtil.join(string.replace(".", File.separator), StringUtil.upFirstChar(name), "Container.java");
	}

	@Override
	public boolean isRewrite() {
		return false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
