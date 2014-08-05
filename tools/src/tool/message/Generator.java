package tool.message;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import tool.ftl.FtlManager;
import tool.ftl.FunctionEnum;
import tool.ftl.IFtl;
import tool.ftl.LanguageEnum;
import tool.message.struct.Bean;
import tool.message.struct.Field;
import tool.message.struct.Handler;
import tool.message.struct.Manager;
import tool.message.struct.Message;
import tool.util.Config;

public class Generator {
	public boolean generate(LanguageEnum type, String xml) throws Exception {
		String managerXmlPath = new StringBuilder().append(Config.getInstance().getPath(type)).append(File.separator).append("message.xml").toString();
		Manager manager = parseManager(managerXmlPath);
		List<IFtl> ftls = parse(xml, type);
		for (IFtl ftl : ftls) {
			FtlManager.getInstance().generate(FunctionEnum.MESSAGE, type, ftl);
			if (ftl instanceof Handler) {
				Handler handler = (Handler)ftl;
				manager.add(handler.getId(), handler.getPkg(), handler.getName());
			}
		}
		FtlManager.getInstance().generate(FunctionEnum.MESSAGE, type, manager);
		record(managerXmlPath, manager);
		return true;
	}
	
	private void record(String path, Manager manager) throws Exception {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("root");
		
		for (Manager.Detail detail : manager.getDetails().values()) {
			Element element = root.addElement("message");
			element.addAttribute("id", String.valueOf(detail.getId()));
			element.addAttribute("pkg", detail.getPkg());
			element.addAttribute("name", detail.getName());
		}
		
		OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");// 设置XML文件的编码格式
		XMLWriter writer = new XMLWriter(new FileWriterWithEncoding(new File(path), "UTF-8"), format);
		writer.write(document);
		writer.close();
	}

	private Manager parseManager(String path) throws Exception {
		Manager manager = new Manager();
		File file = new File(path);
		if (!file.exists()) {
			return null;
		}
		SAXReader reader = new SAXReader();
		Document doc = reader.read(file);
		Element root = doc.getRootElement();
		
		for (Iterator<?> i = root.elementIterator("message"); i.hasNext(); ) {
			Element element = (Element)i.next();
			manager.add(Integer.parseInt(element.attributeValue("id")), element.attributeValue("pkg"), element.attributeValue("name"));
		}
		return manager;
	}

	private List<IFtl> parse(String xml, LanguageEnum type) throws Exception {
		List<IFtl> list = new ArrayList<>();
		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File(xml));
		Element root = doc.getRootElement();
		int index = Integer.parseInt(root.attributeValue("id"));
		String pkg = root.attributeValue("package");
		
		for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
			Element element = (Element)i.next();
			add(list, element, index, pkg, type);
		}
		return list;
	}

	private void add(List<IFtl> list, Element root, int index, String pkg, LanguageEnum type) {
		if (root.getName().equals("bean")) {
			Bean bean = new Bean();
			list.add(bean);
			
			bean.setName(root.attributeValue("name"));
			bean.setNote(root.attributeValue("note"));
			bean.setPkg(pkg);
			
			for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
				Element element = (Element)i.next();
				Field field = new Field();
				bean.getFields().add(field);
				
				field.setClazz(element.attributeValue("class"));
				field.setName(element.attributeValue("name"));
				field.setNote(element.attributeValue("note"));
				if (element.getName().equals("list")) {
					field.setListFlag(1);
				}
			}
		} else if (root.getName().equals("message")) {
			Message message = new Message();
			list.add(message);
			
			message.setName(root.attributeValue("name"));
			message.setNote(root.attributeValue("note"));
			message.setPkg(pkg);
			message.setId(index * 1000 + Integer.parseInt(root.attributeValue("id")));
			
			for (Iterator<?> i = root.elementIterator(); i.hasNext(); ) {
				Element element = (Element)i.next();
				Field field = new Field();
				message.getFields().add(field);
				
				field.setClazz(element.attributeValue("class"));
				field.setName(element.attributeValue("name"));
				field.setNote(element.attributeValue("note"));
				if (element.getName().equals("list")) {
					field.setListFlag(1);
				}
			}
			
			if (root.attributeValue("type").equals(getString(type))) {
				Handler handler = new Handler();
				list.add(handler);
				
				handler.setId(message.getId());
				handler.setName(message.getName());
				handler.setPkg(pkg);
			}
		}
	}

	private Object getString(LanguageEnum type) {
		switch (type) {
		case AS: return "SC";
		case JAVA_CLIENT: return "SC";
		case JAVA_SERVER: return "CS";
		}
		return "";
	}
}
