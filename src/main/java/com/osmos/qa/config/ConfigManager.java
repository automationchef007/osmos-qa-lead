package com.osmos.qa.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

    private static final Properties properties = new Properties();

    static {
        try (InputStream is = ConfigManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String get(String key) {
        return System.getProperty(key, properties.getProperty(key));
    }

    public static String getBaseUrl() {
        return get("base.url");
    }

    public static String getApiBaseUrl() {
        return get("api.base.url");
    }

    public static String getBrowser() {
        return get("browser");
    }

    public static int getImplicitWait() {
        return Integer.parseInt(get("implicit.wait"));
    }

    public static int getExplicitWait() {
        return Integer.parseInt(get("explicit.wait"));
    }
}
