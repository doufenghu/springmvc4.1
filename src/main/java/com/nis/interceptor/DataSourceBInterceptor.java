package com.nis.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.nis.datasource.CustomerContextHolder;

public class DataSourceBInterceptor implements HandlerInterceptor {
	Logger logger = Logger.getLogger(DataSourceBInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info("开启数据源B操作---");
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_B);//开启数据源B
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.info("postHandle---");
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		CustomerContextHolder.clearCustomerType();
		logger.info("释放数据源B操作---");
	}

	
}
