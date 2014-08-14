package tool.db.struct;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.StringUtil;

public class Config implements IFtl {
	private String url;
	private String usr;
	private String pwd;
	private String type;
	private List<String> names = new ArrayList<>();

	@Override
	public HashMap<String, Object> getDataModel() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("url", url);
		map.put("usr", usr);
		map.put("pwd", pwd);
		map.put("path", "com/db/" + type + "/mapper");
		map.put("names", names);
		return map;
	}

	@Override
	public String getFtlFileName() {
		return "config.ftl";
	}

	@Override
	public String getDstPath(LanguageEnum language) {
		String path = tool.util.Config.getInstance().getPath(language);
		String string = StringUtil.join(path.substring(0, path.length() - 4), File.separator, "config", File.separator);
		return StringUtil.join(string.replace(".", File.separator), "db-", type, ".xml");
	}

	@Override
	public boolean isRewrite() {
		return true;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}
