/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.database;

import java.util.HashMap;

public class DatabaseManager {

    private static DatabaseManager mInstance = null;

    private HashMap<String, Object> mConnectionsMap;

    private String mysql_url = "jdbc:mysql://192.168.11.169:3306/moviedatabase";
    private String mysql_user = "sscraper";
    private String mysql_pass = "superscraper";
    private String mysql_driver =  "com.mysql.jdbc.Driver";
    
    public static DatabaseManager getInstance() {
        if (mInstance == null) {
            mInstance = new DatabaseManager();
        }

        return mInstance;
    }

    public DatabaseManager() {
        // TODO : local database by configure
        
        DbConnectionHelper connectHelper = new DbConnectionHelper(mysql_url, mysql_user, mysql_pass, mysql_driver,
                5, 50, 20, 1000, 1);
        
        mConnectionsMap.put("mysql", connectHelper);        
    }
    
    public DbConnectionHelper getConnectionHelper(String name) {
        return (DbConnectionHelper) mConnectionsMap.get(name);
    }
}
