/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.sscraper.utils.Log;

public class DbConnectionHelper {
    private static DataSource mDataSource;

    public Connection getConnection() {
        Connection con = null;
        if (mDataSource != null) {
            try {
                con = mDataSource.getConnection();
            } catch (Exception e) {
                Log.printStackTrace(e);
                return null;
            }

            try {
                con.setAutoCommit(false);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return con;
        }
        return con;
    }

    public DbConnectionHelper(String connectUrl, String username, String pswd,
            String driverClass, int initialSize, int maxActive, int maxIdle,
            int maxWait, int minIdle) {
        initDataSource(connectUrl, username, pswd, driverClass, initialSize,
                maxActive, maxIdle, maxWait, minIdle);
    }

    /**
     * Initialize data source 
     * Fox example :
     * initDataSource(connectURI, "root", "password", "com.mysql.jdbc.Driver",
                5, 100, 30, 10000, 1);
     * @param connectUrl
     * @param username
     * @param pswd
     * @param driverClass
     * @param initialSize
     * @param maxtotal
     * @param maxIdle
     * @param maxWaitMillis
     * @param minIdle
     */
    public static void initDataSource(String connectUrl, String username,
            String pswd, String driverClass, int initialSize, int maxtotal,
            int maxIdle, int maxWaitMillis, int minIdle) {
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driverClass);
        ds.setUsername(username);
        ds.setPassword(pswd);
        ds.setUrl(connectUrl);
        ds.setInitialSize(initialSize); // initialize connection counter
        ds.setMaxTotal(maxtotal);
        ds.setMaxIdle(maxIdle);
        ds.setMaxWaitMillis(maxWaitMillis);
        ds.setMinIdle(minIdle);
        mDataSource = ds;
    }

    /**
     * Get connect status
     * 
     * @return
     * @throws SQLException
     */
    public Map<String, Integer> getDataSourceStatus()
            throws SQLException {
        BasicDataSource bds = (BasicDataSource) mDataSource;
        Map<String, Integer> map = new HashMap<String, Integer>(2);
        map.put("active_number", bds.getNumActive());
        map.put("idle_number", bds.getNumIdle());
        return map;
    }

    /**
     * Shutdown datasource connection
     * 
     * @throws SQLException
     */
    public void shutdownDataSource() throws SQLException {
        BasicDataSource bds = (BasicDataSource) mDataSource;
        bds.close();
    }
}
