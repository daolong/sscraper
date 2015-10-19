/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sscraper.Status;
import org.sscraper.model.MovieInfo;
import org.sscraper.utils.Log;

public class DatabaseHelper {
    private static String TAG = "DatabaseHelper";
    
    private DatabaseManager mDbManager;

    DatabaseHelper() {
        createTable();
        
        mDbManager = DatabaseManager.getInstance();
    }
    
    private Connection getMysqlConnection() {
        DbConnectionHelper dbconnhelper = mDbManager.getConnectionHelper("mysql");
        if (dbconnhelper != null) {
            return dbconnhelper.getConnection();
        }
        
        return null;
    }
    
    
    private void createTable() {
        Connection conn = null;
        Statement stmt = null;
        String sql = MovieInfo.getMysqlCreateTableCommand();
        try {
            conn = getMysqlConnection();
            if (conn == null)
                return;
            stmt = conn.createStatement();
            //executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            int result = stmt.executeUpdate(sql);        
            Log.d(TAG, "create table result = " + result);
        } catch (Exception e) {
            e.printStackTrace();
        } 
        finally {
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public int insertMovie(MovieInfo movie) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int id = -1;
        String sql = movie.getInsertSqlCmd();
        try {
            conn = getMysqlConnection();
            if (conn == null)
                return id;
            
            pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            movie.setToStatement(pstmt);
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                movie.setZmdbId((long) id);
            } else {
                // throw an exception from here
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
        finally {            
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {e.printStackTrace();}
            }
            
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {e.printStackTrace();}
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {e.printStackTrace();}
            }
        }
        
        
        return id;
    }
}
