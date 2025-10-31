package com.edureka.parameter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {
    private static Properties properties;

    static {
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream("src/test/resources/PropertyData/data.properties");
            properties.load(fis);
        } catch (IOException e) {
            System.out.println("Failed to load properties file: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static Properties getAllProperties() {
        return properties;
    }
}