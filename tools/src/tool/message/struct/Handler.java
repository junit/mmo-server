package tool.message.struct;

import java.io.File;
import java.util.HashMap;

import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.Config;
import tool.util.StringUtil;

public class Handler implements IFtl {
	private int id;
	private String pkg;
	private String name;

	@Override
	public HashMap<String, Object> getDataModel() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pkg", pkg);
		map.put("name", name);
		return map;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getFtlFileName() {
		return "handler.ftl";
	}

	@Override
	public String getDstPath(LanguageEnum language) {
		String path = StringUtil.join(Config.getInstance().getPath(language), File.separator, pkg, File.separator, "handler", File.separator, name);
		return StringUtil.join(path.replace(".", File.separator), "Handler.java");
	
	}

	@Override
	public boolean isRewrite() {
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
