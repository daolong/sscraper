/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */

package org.sscraper;

import java.util.ArrayList;
import java.util.List;

import org.sscraper.database.DatabaseHelper;
import org.sscraper.database.android.AndroidHelper;
import org.sscraper.database.mysql.MysqlHelper;
import org.sscraper.model.MovieInfo;
import org.sscraper.network.HttpUtils;
import org.sscraper.scraper.*;
import org.sscraper.utils.AppConstants;
import org.sscraper.utils.Log;

public class ScraperProcess {
    static final String TAG = "Scraper.Process";

    private List<ScraperBase> mScrapers;
    DatabaseHelper helper;
    
    public ScraperProcess() {
    	// We will return the first result of the dounban to user if the name is not match exactly.
    	// so we put douban after tmdb
        registerScrpaer((ScraperBase)new TmdbScraper());
        registerScrpaer(new DoubanScraper());
        //registerScrpaer(new M1905Scraper());
    }
    
    private void registerScrpaer(ScraperBase scraper) {
        if (mScrapers == null) {
            mScrapers = new ArrayList<ScraperBase>();
        }
        
        mScrapers.add(scraper);
    }
    
    /**
     * Pre-process string for sql query.
     * @param str
     * @return
     */
    private String preProcess(String str) {
    	if (str == null)
    		return null;
    	if (AppConstants.SERVER) {
    		// Mysql "'" ---> "\\\'"
    		return str.replace("\'", "\\\\\\\'");
    	} else {
    		// Sqlite "'" --> "''"
    		return str.replace("\'", "\'\'");
    	}
    }
    
    public MovieInfo queryMovie(String title, String year) {
    	if (title == null) {
    		return null;
    	}
    	
        String queryUtf8 = title;
        if (AppConstants.SERVER)
        	queryUtf8 = HttpUtils.decodeHttpParam(title, "UTF-8"); 
        
        // TODO guess the movie title
        
        // query from data base first
        MovieInfo movie = null;
        try {
        	movie = helper.queryMovieByOriginalTitle(preProcess(queryUtf8), year);
        } catch (Exception e) {
        	Log.w(TAG, "query movie (" + queryUtf8 + ") get exception");
        	Log.printStackTrace(e);
        }
        
        if (movie == null) {     
            // not found in data base, scraper from Internet
            for (int i = 0; i < mScrapers.size(); i++) {
                movie = mScrapers.get(i).findMovie(title, year);
                if (movie != null) {
                    movie.setOriginalSearchTitle(queryUtf8);
                    // add to data base
                    long id = -1;
                    try {
                    	id = helper.insertMovie(movie);
                    	if (id < 0) {
                    		Log.w(TAG, "insert (" + queryUtf8 + ") to db fail. Should to check!!!!");
                    		movie = null;
                    		continue;
                    	}
                    } catch (Exception e) {
                    	Log.w(TAG, "insert (" + queryUtf8 + ") to db exception. Should to check!!!!");
                    	Log.printStackTrace(e);
                    	movie = null;
                    	continue;                    
                    }
                    break;
                }
            }
        }
        
        return movie;
    }
    
    /**
     * Api for scraper movie on Android device.
     * @param title The tile of movie
     * @param year  The release year of movie
     * @return  The movie information if found, null if not found
     */
    public MovieInfo findMovie4Local(String title, String year) {
        helper = new AndroidHelper();
        return queryMovie(title, year);
    }
 
    /**
     * Api for scraper movie on server.
     * @param title The tile of movie
     * @param year  The release year of movie
     * @return  Response as json string. </p>
     *          The status is 1000 if find the movie, other status means not found
     */
    public String findMovie4Server(String title, String year) {
       helper = new MysqlHelper();
       MovieInfo movie = queryMovie(title, year);
       if (movie != null) {
           Response res = new Response(Status.OK);
           res.addMovie(movie);
           return res.toJsonString(); 
       }
       
       return (new Response(Status.NOT_FOUND)).toJsonString();
       
    }   
   
}
