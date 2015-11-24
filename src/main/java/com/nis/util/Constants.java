package com.nis.util;

public final class Constants {
	
	public static final String DEFAULT_CAPTCHA_PARAM = "captcha";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";
	
	/**
	 * 词典数据key
	 */
	public static final String CACHE_DICT_MAP = "dictMap";
	
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";

	/**
	 * 是/否
	 */
	public static final String YES = "1";
	public static final String NO = "0";
	
	/**
	 * 对/错
	 */
	public static final String TRUE = "true";
	public static final String FALSE = "false";
	
	/**
	 * 上传文件基础虚拟路径
	 */
	public static final String USERFILES_BASE_URL = "/userfiles/";
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	public static final int LOG_ACCESS_SUCCESS = 1;
	public static final int LOG_ACCESS_EXCEPTION = 0;
	/**
	 * 默认未知方法（未添加词典或未识别）操作类型值为：unknown（8000）
	 */
	public static final int DEFAULT_METHOD_TYPE = 8000;
	
	

}
