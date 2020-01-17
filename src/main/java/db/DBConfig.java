package db;

import lombok.extern.log4j.Log4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Log4j
public class DBConfig {
    private static final String DB_FILE_PROPERTIES = "src/main/resources/db.properties";
    private static Properties properties = new Properties();

    private DBConfig(){

    }

    static {
        try {
            properties.load(new FileInputStream(DB_FILE_PROPERTIES));
        } catch (IOException e) {
           log.error("Could not load properties for database config", e);
        }
    }

    public static String getDriver() {
        return properties.getProperty("driver");
    }

    public static String getUrl() {
        return properties.getProperty("url");
    }

    public static String getUser() {
        return properties.getProperty("user");
    }

    public static String getPassword() {
        return properties.getProperty("password");
    }
}