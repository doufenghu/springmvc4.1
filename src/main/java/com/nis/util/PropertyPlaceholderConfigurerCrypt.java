package com.nis.util;

import java.util.Properties;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.nis.crypt.AESUtil;

public class PropertyPlaceholderConfigurerCrypt extends
		PropertyPlaceholderConfigurer {

	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		
		try {
			String productPassword = props.getProperty("jdbc.product.password");
			String productScretKey = props.getProperty("jdbc.product.key");
			if (null != productPassword) {
				props.setProperty("jdbc.product.password", new String(AESUtil.decrypt(Base64.decodeBase64(productPassword), productScretKey)));
			}
			
			String devlopPassword = props.getProperty("jdbc.devlop.password");
			String devlopScretKey = props.getProperty("jdbc.devlop.key");
			if (null != productPassword) {
				props.setProperty("jdbc.devlop.password", new String(AESUtil.decrypt(Base64.decodeBase64(devlopPassword), devlopScretKey)));
			}
			
			System.out.println(props.getProperty("jdbc.product.password")+" "+props.getProperty("jdbc.devlop.password"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		super.processProperties(beanFactoryToProcess, props);
	}

}
