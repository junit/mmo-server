package tool.ftl;

import java.util.HashMap;

public interface IFtl {

	public HashMap<String, Object> getDataModel();

	public String getFtlFileName();

	public String getDstPath(LanguageEnum language);

	public boolean isRewrite();

}
