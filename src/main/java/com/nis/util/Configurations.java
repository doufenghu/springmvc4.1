package com.nis.util;
import java.io.File;
import java.util.Map;
import java.util.Properties;
import com.nis.util.StringUtil;




public final class Configurations {
	private static Properties prop = new Properties();

	static {
		try {
			prop.load(Configurations.class.getResourceAsStream("/nis.properties"));
		} catch (Exception e) {
			prop = null;
			System.err.println("未知nis.properties,请确定文件是否存在!");
		}
	}

	public static String getStringProperty(String key, String defaultValue) {
		if (prop==null||StringUtil.isBlank(prop.getProperty(key))) {
			return defaultValue;
		}
		return prop.getProperty(key).trim();
	}

	public static int getIntProperty(String key, int defaultValue) {
		if (prop==null||StringUtil.isBlank(prop.getProperty(key))) {
			return defaultValue;
		}
		return Integer.parseInt(prop.getProperty(key).trim());
	}

	public static long getLongProperty(String key, long defaultValue) {
		if (prop==null||StringUtil.isBlank(prop.getProperty(key))) {
			return defaultValue;
		}
		return Long.parseLong(prop.getProperty(key).trim());
	}

	public static boolean getBooleanProperty(String key, boolean defaultValue) {
		if (prop==null||StringUtil.isBlank(prop.getProperty(key))) {
			return defaultValue;
		}
		return prop.getProperty(key).toLowerCase().trim().equals("true");
	}

	public static String getFileDirPathProperty(String key,
			String defaultValue) {
		if (prop==null||StringUtil.isBlank(prop.getProperty(key))) {
			return defaultValue;
		}
		String path = prop.getProperty(key).trim();
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		return file.getAbsolutePath();
	}

	public static boolean configPropertyIsFound() {
		if (prop == null) {
			return false;
		}
		return true;
	}
	
	
	public static Map getProp() {
		return prop;
	}

	
	
	

	

}
