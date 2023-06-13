package com.iotassistant.utils;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


public class ApplicationProperties {
	
	private static volatile ApplicationProperties INSTANCE = null;
	
	private static final String APPLICATION_PROPERTIES_PATH = "/application.properties";
	
	Properties properties = null;
	
	private ApplicationProperties() {
		super();
		try {
			Resource resource = new ClassPathResource(APPLICATION_PROPERTIES_PATH);
			properties = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ApplicationProperties getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ApplicationProperties();
		}
		return INSTANCE;
	}
	
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	
	public String getProperty(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

}
