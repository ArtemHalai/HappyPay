package util;

import lombok.extern.log4j.Log4j;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class ConnectionClosure {

    public static void close(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("SQLException occurred in ConnectionClosure.class at close() method");
        }
    }
}
