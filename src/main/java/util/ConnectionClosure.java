package util;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionClosure {

    private static final Logger log = Logger.getLogger(ConnectionClosure.class);

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("SQLException occurred in ConnectionClosure.class at close() method");
        }
    }
}
