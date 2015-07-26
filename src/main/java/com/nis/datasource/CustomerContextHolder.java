package com.nis.datasource;

public class CustomerContextHolder {
	public static final String DATA_SOURCE_A = "dataSourceA";
	public static final String DATA_SOURCE_B = "dataSourceB";
	public static final String DATA_SOURCE_C = "dataSourceC";
	public static final String DATA_SOURCE_D = "dataSourceD";
	public static final String DATA_SOURCE_E = "dataSourceE";
	
	//线程本地环境
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	//设置数据源类型
	public static void setCustomerType(String customerType){
		contextHolder.set(customerType);
	}
	//获取数据源类型
	public static String getCustomerType(){
		return contextHolder.get();
	}
	//清除数据源类型
	public static void clearCustomerType(){
		contextHolder.remove();
	}

}
