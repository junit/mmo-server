package tool.ftl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.commons.io.output.FileWriterWithEncoding;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

public class FtlManager {
	private static FtlManager instance = new FtlManager();

	public static FtlManager getInstance() {
		return instance;
	}

	private FtlManager() {
		init(FunctionEnum.DB, LanguageEnum.JAVA_CLIENT);
		init(FunctionEnum.DB, LanguageEnum.JAVA_SERVER);
		init(FunctionEnum.DB, LanguageEnum.AS);

		init(FunctionEnum.MESSAGE, LanguageEnum.JAVA_CLIENT);
		init(FunctionEnum.MESSAGE, LanguageEnum.JAVA_SERVER);
		init(FunctionEnum.MESSAGE, LanguageEnum.AS);
	}

	private void init(FunctionEnum function, LanguageEnum language) {
		HashMap<LanguageEnum, HashMap<String, Template>> language2name2template = function2language2name2template.get(function);
		if (language2name2template == null) {
			language2name2template = new HashMap<>();
			function2language2name2template.put(function, language2name2template);
		}
		HashMap<String, Template> name2template = language2name2template.get(language);
		if (name2template == null) {
			name2template = new HashMap<>();
			language2name2template.put(language, name2template);
		}

		String path = getPath(function, language);
		Configuration cfg = new Configuration();
		try {
			cfg.setDefaultEncoding("UTF-8");

			File dir = new File(path);
			cfg.setDirectoryForTemplateLoading(dir);
			cfg.setObjectWrapper(new DefaultObjectWrapper());

			for (File file : dir.listFiles()) {
				name2template.put(file.getName(), cfg.getTemplate(file.getName()));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getPath(FunctionEnum function, LanguageEnum language) {
		StringBuilder builder = new StringBuilder();
		builder.append("ftl").append(File.separator);
		switch (function) {
		case MESSAGE:
			builder.append("message");
			break;
		case DB:
			builder.append("db");
			break;
		default:
			return null;
		}
		
		builder.append(File.separator);
		
		switch (language) {
		case AS:
			builder.append("as");
			break;
		case JAVA_CLIENT:
			builder.append("java");
			break;
		case JAVA_SERVER:
			builder.append("java");
			break;
		default:
			return null;
		}
		
		return builder.toString();
	}

	private HashMap<FunctionEnum, HashMap<LanguageEnum, HashMap<String, Template>>> function2language2name2template = new HashMap<>();

	private void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}

	public boolean generate(FunctionEnum function, LanguageEnum language, IFtl ftl) throws Exception {
		if (function2language2name2template == null) {
			return false;
		}
		HashMap<LanguageEnum, HashMap<String, Template>> language2name2template = function2language2name2template.get(function);
		if (language2name2template == null) {
			return false;
		}
		HashMap<String, Template> name2template = language2name2template.get(language);
		if (name2template == null) {
			return false;
		}
		Template template = name2template.get(ftl.getFtlFileName());
		if (template == null) {
			return false;
		}

		File file = new File(ftl.getDstPath(language));
		if (file.exists() && !ftl.isRewrite()) {
			return true;
		}
		if (!file.getParentFile().exists()) {
			mkDir(file.getParentFile());
		}
		file.createNewFile();
		template.process(ftl.getDataModel(), new FileWriterWithEncoding(file, "UTF-8"));
		return true;
	}
}
