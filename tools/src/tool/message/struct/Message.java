package tool.message.struct;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.util.Config;
import tool.util.StringUtil;

public class Message implements IFtl {
	private String pkg;
	private String note;
	private String name;
	private int id;
	private List<Field> fields = new ArrayList<>();

	@Override
	public HashMap<String, Object> getDataModel() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pkg", pkg);
		map.put("note", note);
		map.put("name", name);
		map.put("fields", fields);
		map.put("id", id);
		return map;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getFtlFileName() {
		return "message.ftl";
	}

	@Override
	public String getDstPath(LanguageEnum language) {String path = StringUtil.join(Config.getInstance().getPath(language), File.separator, pkg, File.separator, name);
	return StringUtil.join(path.replace(".", File.separator), "Message.java");}

	@Override
	public boolean isRewrite() {
		return true;
	}
}
