package factories;

import db.DBConfig;
import org.apache.log4j.Logger;
import org.apache.tomcat.dbcp.dbcp.ConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.DriverManagerConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolableConnectionFactory;
import org.apache.tomcat.dbcp.dbcp.PoolingDataSource;
import org.apache.tomcat.dbcp.pool.impl.GenericObjectPool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A class that provides factory to get connection from connection pool for JDBC.
 */
public class JDBCConnectionFactory {

    private static GenericObjectPool genPool = null;
    private static volatile JDBCConnectionFactory connectionFactory = null;
    private DataSource dataSource = null;
    private static final Logger LOG = Logger.getLogger(JDBCConnectionFactory.class);

    /**
     * Private constructor to prevent
     * the instantiation of this class directly.
     * Initialize GenericObjectPool {@link #genPool}, set max amount of active connections to it.
     * Initialize DataSource {@link #dataSource}.
     * @see DBConfig
     */
    private JDBCConnectionFactory() {
        try {
            Class.forName(DBConfig.getDriver());
            genPool = new GenericObjectPool();
            genPool.setMaxActive(30);
            ConnectionFactory factory = new DriverManagerConnectionFactory(DBConfig.getUrl(), DBConfig.getUser(), DBConfig.getPassword());
            PoolableConnectionFactory poolableConnectionFactory =
                    new PoolableConnectionFactory
                            (factory, genPool, null, null, false, true);
            dataSource = new PoolingDataSource(genPool);
        } catch (ClassNotFoundException e) {
            LOG.error("ClassNotFoundException occurred in JDBCConnectionFactory.class at private constructor");
        }
    }

    /**
     * Gets the instance of this class.
     *
     * @return the instance of {@link #connectionFactory}.
     */
    public static JDBCConnectionFactory getInstance() {
        if (connectionFactory == null) {
            synchronized (JDBCConnectionFactory.class) {
                if (connectionFactory == null) {
                    connectionFactory = new JDBCConnectionFactory();
                }
            }
        }
        return connectionFactory;
    }

    /**
     * Gets the connection from dataSource.
     *
     * @return the connection from {@link #dataSource}.
     */
    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in JDBCConnectionFactory.class at getConnection() method");
        }
        return connection;
    }
}
