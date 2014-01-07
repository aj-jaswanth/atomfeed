package org.ict4h.atomfeed;


import org.ict4h.atomfeed.jdbc.JdbcConnectionProvider;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public abstract class IntegrationTest {
    private ResourceBundle bundle = ResourceBundle.getBundle("atomfeed");

    protected Connection getConnectionFromDriverManager() throws SQLException {
        //System.out.println(String.format("jdbc url: %s, username: %s, password: %s", bundle.getString("jdbc.url"), bundle.getString("jdbc.username"), bundle.getString("jdbc.password")));
        return DriverManager.getConnection(bundle.getString("jdbc.url"),
                bundle.getString("jdbc.username"),
                bundle.getString("jdbc.password")
        );
    }

    protected JdbcConnectionProvider getConnectionProvider() {
        return new JdbcConnectionProvider() {
            private Connection providedConnection = null;
            @Override
            public Connection getConnection() throws SQLException {
                if (providedConnection == null) {
                    providedConnection = getConnectionFromDriverManager();
                    providedConnection.setAutoCommit(false);
                }
                return providedConnection;
            }

            @Override
            public void closeConnection(Connection connection) throws SQLException {
                if (connection != null) {
                    connection.close();
                }
                this.providedConnection = null;
            }
        };
    }

    protected String getProperty(String key) {
        return bundle.getString(key);
    }
}