package com.bookstore.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        try {
            FileInputStream input = new FileInputStream("src/test/resources/config.properties");
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties file: " + e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }

    public static String getBaseUrl() {
    	String value = properties.getProperty("baseurl");
    	if (value == null || value.isEmpty()) {
            throw new RuntimeException("Missing required config: baseurl");
        }
    	
        return value;
    }
}
