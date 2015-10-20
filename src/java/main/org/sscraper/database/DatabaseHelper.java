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

    public DatabaseHelper() {
        mDbManager = DatabaseManager.getInstance();
        createTable();
    }
    
    /**
     * Get mysql connection
     * @return
     */
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
        Log.d(TAG, "create : " + sql);
        //String sql = "create table if not exists student(NO char(20),name varchar(20),primary key(NO))";
        try {
            conn = getMysqlConnection();
            if (conn == null)
                return;
            stmt = conn.createStatement();
            //executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
            int result = stmt.executeUpdate(sql);        
            Log.d(TAG, "create table result = " + result);
        } catch (Exception e) {
            Log.printStackTrace(e);
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
    
    public long insertMovie(MovieInfo movie) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        long id = -1;
        String sql = movie.getInsertSqlCmd();
        //String sql = "insert into student(NO,name) values('2012003','xueyouzhang')";
        try {
            conn = getMysqlConnection();
            if (conn == null)
                return id;
            
            
            pstmt = (PreparedStatement) conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            movie.setToStatement(pstmt);
            int ret = pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getLong(1);
                Log.d(TAG, "movie db id  = " + id);
                movie.setZmdbId(id);
            } else {
                // throw an exception from here
                Log.d(TAG, "insert movie fail");
            }
            
            /*
            Statement stmt = conn.createStatement();
            int ret = stmt.executeUpdate(sql);            
            Log.d(TAG, "execute insert ret = " + ret);
            stmt.close();
            */
        } catch (Exception e) {
            Log.printStackTrace(e);
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
    
    /**
     * Query movie by original search title form client
     * @param originalTitle Original search title
     * @return
     */
    public MovieInfo queryMovieByOriginalTitle(String originalTitle) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM movies WHERE original_search_title='" + originalTitle + "'";
        //String sql = "SELECT * FROM student";
        try {
            conn = getMysqlConnection();
            if (conn == null)
                return null;
            
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql); 
            /*
            while (rs.next()) {
                System.out.println(rs.getString(1) + "\t" + rs.getString(2));
            }
            */

            if (rs.next()) {
                return new MovieInfo(rs);
            } else {
                Log.d(TAG, "query movie <" + originalTitle + "> no result");
            }
        } catch (Exception e) {
            Log.printStackTrace(e);
        } finally {            
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {e.printStackTrace();}
            }
            
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {e.printStackTrace();}
            }
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {e.printStackTrace();}
            }
        }
        
        return null;
    }
}
