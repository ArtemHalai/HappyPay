package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The class that provides configuration for database connection.
 */
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

    /**
     * Gets the driver.
     *
     * @return the string value of driver from {@link #properties}.
     */
    public static String getDriver() {
        return properties.getProperty("driver");
    }

    /**
     * Gets the url.
     *
     * @return the string value of url from {@link #properties}.
     */
    public static String getUrl() {
        return properties.getProperty("url");
    }

    /**
     * Gets the user.
     *
     * @return the string value of user from {@link #properties}.
     */
    public static String getUser() {
        return properties.getProperty("user");
    }

    /**
     * Gets the password.
     *
     * @return the string value of password from {@link #properties}.
     */
    public static String getPassword() {
        return properties.getProperty("password");
    }
}