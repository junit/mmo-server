package tool.util;

public class StringUtil {
	public static String join(String... strs) {
		StringBuilder builder = new StringBuilder();
		for (String str : strs) {
			builder.append(str);
		}
		return builder.toString();
	}

	public static String upFirstChar(String str) {
		return new StringBuilder().append(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
	}
	
	public static String lowFirstChar(String str) {
		return new StringBuilder().append(str.substring(0, 1).toLowerCase()).append(str.substring(1)).toString();
	}
}
