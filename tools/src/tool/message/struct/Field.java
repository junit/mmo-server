package tool.message.struct;

public class Field {
	private String clazz;
	private String name;
	private String note;
	private int listFlag; // 0非list 1为list

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getListFlag() {
		return listFlag;
	}

	public void setListFlag(int listFlag) {
		this.listFlag = listFlag;
	}
}
