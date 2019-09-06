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

public class JDBCConnectionFactory {

    private static GenericObjectPool genPool = null;
    private static volatile JDBCConnectionFactory connectionFactory = null;
    private DataSource dataSource = null;
    private static final Logger LOG = Logger.getLogger(JDBCConnectionFactory.class);

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

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOG.error("SQLException occurred in JDBCConnectionFactory.class at getConnection() method");
        }
        return connection;
    }

    public static GenericObjectPool getConnectionPool() {
        return genPool;
    }
}
