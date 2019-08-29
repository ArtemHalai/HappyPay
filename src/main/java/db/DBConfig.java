package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {
    private static final String dbFileProperties = "src/main/resources/db.properties";
    private static Properties properties = new Properties();

    static {
        try {
            properties.load(new FileInputStream(dbFileProperties));
        } catch (IOException e) {
            e.printStackTrace();
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