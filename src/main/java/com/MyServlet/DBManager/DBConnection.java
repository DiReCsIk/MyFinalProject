package com.MyServlet.DBManager;

import com.MyServlet.Exception.ConnectionException;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnection implements Closeable {
    private static final Logger log = Logger.getLogger(DBConnection.class.getName());

    private static final BasicDataSource basicDataSource;
    private Connection connection;

    private DBConnection() {
    }

    static {
        log.info("Start initializing DBConnection");
        ResourceBundle dbConfig = ResourceBundle.getBundle("/sql/dbConfig");
        int maxActive = Integer.parseInt(dbConfig.getString("maxActive"));
        int minIdle = Integer.parseInt(dbConfig.getString("minIdle"));
        int maxIdle = Integer.parseInt(dbConfig.getString("maxIdle"));
        int maxOpenPStatements = Integer.parseInt(dbConfig.getString("maxOpenPreparedStatements"));
        String url = "jdbc:mysql://" +
                dbConfig.getString("host") + ":" +
                dbConfig.getString("port") + "/" +
                dbConfig.getString("database") + "?useUnicode=" +
                dbConfig.getString("useUnicode") + "&characterEncoding=" +
                dbConfig.getString("encoding") + "&serverTimezone=" +
                dbConfig.getString("timeZone");
        basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        basicDataSource.setUsername(dbConfig.getString("userName"));
        basicDataSource.setPassword(dbConfig.getString("password"));
        basicDataSource.setUrl(url);
        basicDataSource.setMaxActive(maxActive);
        basicDataSource.setMinIdle(minIdle);
        basicDataSource.setMaxIdle(maxIdle);
        basicDataSource.setMaxOpenPreparedStatements(maxOpenPStatements);
        log.info("Finish initializing DBConnection");
    }

    public Connection getConnection() {
        return connection;
    }

    public static DBConnection createConnection() throws ConnectionException {
        DBConnection dbConnection = new DBConnection();
        try {
            dbConnection.connection = basicDataSource.getConnection();
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new ConnectionException(exception.getMessage(), exception);
        }
        return dbConnection;
    }

    public void startTransaction() throws ConnectionException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new ConnectionException(exception.getMessage(), exception);
        }
    }

    public void commit() throws ConnectionException {
        try {
            connection.commit();
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new ConnectionException(exception.getMessage(), exception);
        }
    }

    public void rollback() throws ConnectionException {
        try {
            connection.rollback();
        } catch (SQLException exception) {
            log.error("Error! " + exception);
            throw new ConnectionException(exception.getMessage(), exception);
        }
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                log.error("Error! " + exception);
            }
        }
    }
}
