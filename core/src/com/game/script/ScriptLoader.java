package com.game.script;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.game.script.struct.IScript;

public class ScriptLoader {
	public HashMap<Integer, IScript> loadJar(String fileName) throws Exception {
		HashMap<Integer, IScript> scripts = new HashMap<Integer, IScript>();
		final File file = new File(fileName);
		URL[] urls = new URL[] { file.toURI().toURL() };
		final URLClassLoader urlClassLoader = new URLClassLoader(urls);
		final List<Class<?>> classesFound = loadClass(urlClassLoader, file);
		for (final Class<?> clazz : classesFound) {
			Object object = clazz.newInstance();
			if (object instanceof IScript) {
				IScript script = (IScript)object;
				scripts.put(script.getId(), script);
			}
		}
		return scripts;
	}

	private List<Class<?>> loadClass(URLClassLoader loader, final File file) throws Exception {
		final List<Class<?>> clazzes = new ArrayList<Class<?>>();
		@SuppressWarnings("resource")
		JarFile jarFile = new JarFile(file);
		Enumeration<JarEntry> entries = jarFile.entries();
		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (!entry.getName().endsWith("Script.class")) {
				continue;
			}
			Class<?> clazz = loader.loadClass(convert(entry.getName()));
			clazzes.add(clazz);
		}
		return clazzes;
	}

	/**
	 * 路径转换成类名s
	 */
	private String convert(final String path) {
		final String classString = path.replace(File.separator, ".").replace("/", ".").replace("\\", "").replace(".class", "");
		return classString;
	}
}
