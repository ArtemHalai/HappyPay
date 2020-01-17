package factories;

import db.DBConfig;
import lombok.extern.log4j.Log4j;
import org.apache.tomcat.dbcp.dbcp.ConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Log4j
public class JDBCConnectionFactory {

    private static GenericObjectPool genPool = null;
    private static JDBCConnectionFactory connectionFactory = null;
    private static DataSource dataSource = null;

    static {
        try {
            Class.forName(DBConfig.getDriver());
            genPool = new GenericObjectPool();
            genPool.setMaxActive(30);
            ConnectionFactory factory = new DriverManagerConnectionFactory(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
            new PoolableConnectionFactory
                    (factory, genPool, null, null, false, true);
            dataSource = new PoolingDataSource(genPool);
        } catch (ClassNotFoundException e) {
            log.error("ClassNotFoundException occurred in JDBCConnectionFactory.class at private constructor", e);
        }
    }

    private JDBCConnectionFactory() {

    }

    public static synchronized JDBCConnectionFactory getInstance() {
        if (connectionFactory == null) {
            connectionFactory = new JDBCConnectionFactory();
        }
        return connectionFactory;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            log.error("SQLException occurred in JDBCConnectionFactory.class at getConnection() method", e);
        }
        return connection;
    }

    public static GenericObjectPool getConnectionPool() {
        return genPool;
    }
}
