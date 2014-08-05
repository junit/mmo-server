package tool.db.struct;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.Config;
import tool.util.StringUtil;

public class Mapper implements IFtl {
	private String type;
	private String name;
	private List<Field> fields = new ArrayList<>();
	
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

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	@Override
	public HashMap<String, Object> getDataModel() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("type", type);
		map.put("name", name);
		map.put("fields", fields);
		return map;
	}

	@Override
	public String getFtlFileName() {
		return "mapper.ftl";
	}

	@Override
	public String getDstPath(LanguageEnum language) {
		String string = StringUtil.join(Config.getInstance().getPath(language), File.separator, "com.db.", type, ".mapper.");
		return StringUtil.join(string.replace(".", File.separator), StringUtil.upFirstChar(name), ".xml");
	}

	@Override
	public boolean isRewrite() {
		return false;
	}

}
