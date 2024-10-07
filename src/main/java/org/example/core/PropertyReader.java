package org.example.core;

import org.example.model.PropertiesEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    private static final String CONFIG_FILE_NAME = "config.properties";

    public PropertiesEntity getProperties() {
        Properties properties = new Properties();
        PropertiesEntity propertiesEntity = new PropertiesEntity();

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_NAME)) {
            if (inputStream == null) {
                System.out.println("Config file with following name is missing: " + CONFIG_FILE_NAME);
                return null;
            }

            properties.load(inputStream);

            propertiesEntity.setBaseUrl(properties.getProperty("base_url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return propertiesEntity;
    }
}
