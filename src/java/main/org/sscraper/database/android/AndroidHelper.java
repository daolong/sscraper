/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.database.android;

import java.util.ArrayList;
import java.util.List;

import org.sscraper.database.DatabaseHelper;
import org.sscraper.model.MovieInfo;
import org.sscraper.utils.Log;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AndroidHelper implements DatabaseHelper {
    private static String TAG = "AndroidHelper";
    
    private SqliteHelper dbHelper;
    
    public AndroidHelper() {
        dbHelper = SqliteHelper.getInstance();
    }
    
    @Override
    public synchronized long insertMovie(MovieInfo movie) {
    	long lastId = -1;
        if (dbHelper != null) {
            String sql = movie.getInsertSqlCommand();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(sql);
            
            // get the last inserted id
            String query = "SELECT ROWID from movies order by ROWID DESC limit 1";
            Cursor c = db.rawQuery(query, null);
            if (c != null && c.moveToFirst()) {
                lastId = c.getLong(0); //The 0 is the column index, we only have 1 column, so the index is 0
                Log.d(TAG, "insertMovie = " + lastId);
                movie.setZmdbId(lastId);
            }
        }
        
        return lastId;
    }

    @Override
    public MovieInfo queryMovieByOriginalTitle(String originalTitle, String year) {
        MovieInfo info = null;
        if (dbHelper != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM movies WHERE original_search_title like '%" + originalTitle + "%'";
            Cursor cr = db.rawQuery(sql, null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                    //TODO:  maybe match several movies, check the year also 
                	Log.d(TAG, "hit movie");
                    info = new MovieInfo(cr);
                }
                cr.close();
            }
        }        
        return info;
    }

	@Override
	public List<MovieInfo> queryMovies() {
		List<MovieInfo> movies = null;
        if (dbHelper != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM movies";
            Cursor cr = db.rawQuery(sql, null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                	movies = new ArrayList<MovieInfo>();
                	do {                	
                		movies.add(new MovieInfo(cr));
                	} while (cr.moveToNext());
                }
                cr.close();
            }
        }        
        
		return movies;
	}

	@Override
	public List<MovieInfo> queryMoviesByGenre(String genre) {
		List<MovieInfo> movies = null;
        if (dbHelper != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM movies WHERE genres like '%" + genre + "%'";
            Cursor cr = db.rawQuery(sql, null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                	movies = new ArrayList<MovieInfo>();
                	do {                	
                		movies.add(new MovieInfo(cr));
                	} while (cr.moveToNext());
                }
                cr.close();
            }
        }        
        
		return movies;
	}

	@Override
	public List<MovieInfo> queryMoviesByYear(String year) {
		List<MovieInfo> movies = null;
        if (dbHelper != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM movies WHERE release_date like '%" + year + "%'";
            Cursor cr = db.rawQuery(sql, null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                	movies = new ArrayList<MovieInfo>();
                	do {                	
                		movies.add(new MovieInfo(cr));
                	} while (cr.moveToNext());
                }
                cr.close();
            }
        }        
        
		return movies;
	}

	@Override
	public List<MovieInfo> queryMoviesByDirector(String director) {
		List<MovieInfo> movies = null;
        if (dbHelper != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM movies WHERE directors like '%" + director + "%'";
            Cursor cr = db.rawQuery(sql, null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                	movies = new ArrayList<MovieInfo>();
                	do {                	
                		movies.add(new MovieInfo(cr));
                	} while (cr.moveToNext());
                }
                cr.close();
            }
        }        
        
		return movies;
	}

	@Override
	public List<MovieInfo> queryMoviesByActor(String actor) {
		List<MovieInfo> movies = null;
        if (dbHelper != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            String sql = "SELECT * FROM movies WHERE actors like '%" + actor + "%'";
            Cursor cr = db.rawQuery(sql, null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                	movies = new ArrayList<MovieInfo>();
                	do {                	
                		movies.add(new MovieInfo(cr));
                	} while (cr.moveToNext());
                }
                cr.close();
            }
        }        
        
		return movies;
	}

	@Override
	public List<MovieInfo> queryMoviesByCustomer(String sqlString) {
		List<MovieInfo> movies = null;
        if (dbHelper != null) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cr = db.rawQuery(sqlString, null);
            if (cr != null) {
                if (cr.moveToFirst()) {
                	movies = new ArrayList<MovieInfo>();
                	do {                	
                		movies.add(new MovieInfo(cr));
                	} while (cr.moveToNext());
                }
                cr.close();
            }
        }        
        
		return movies;
	}

	@Override
	public void updateMovie(MovieInfo movie) {
        if (dbHelper != null) {
            String sql = movie.getUpdateSqlCommand();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.execSQL(sql);
        }
	}

}
