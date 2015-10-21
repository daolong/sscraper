/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */

package org.sscraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sscraper.utils.Log;
import org.sscraper.database.DatabaseHelper;
import org.sscraper.model.MovieInfo;
import org.sscraper.network.HttpUtils;
import org.sscraper.scraper.*;

public class ScraperProcess {
    static final String TAG = "Scraper.Process";

    private List<ScraperBase> mScrapers;
    private String query;
    private String year;
    
    public ScraperProcess(String query, String year) {
        this.query = query;
        this.year = year;
        
        registerScrpaer((ScraperBase)new TmdbScraper());
        registerScrpaer(new M1905Scraper());
        registerScrpaer(new DoubanScraper());
    }
    
    private void registerScrpaer(ScraperBase scraper) {
        if (mScrapers == null) {
            mScrapers = new ArrayList<ScraperBase>();
        }
        
        mScrapers.add(scraper);
    }
    
    public String findMovie() {
       String queryUtf8 = HttpUtils.decodeHttpParam(query, "UTF-8"); 
       // TODO guess the movie title
       
        
       // query form data base first
       DatabaseHelper helper = new DatabaseHelper();
       MovieInfo movie = helper.queryMovieByOriginalTitle(queryUtf8);
       
       if (movie == null) {     
           // not found in data base, scraper from Internet
           for (int i = 0; i < mScrapers.size(); i++) {
               movie = mScrapers.get(i).findMovie(query, year);
               if (movie != null) {
                   movie.setOriginalSearchTitle(queryUtf8);
                   // add to data base
                   helper.insertMovie(movie);
                   break;
               }
           }
       } 
       
       if (movie != null) {
           Response res = new Response(Status.OK);
           res.addMovie(movie);
           return res.toJsonString(); 
       }
       
       return (new Response(Status.NOT_FOUND)).toJsonString();
       
    }
    
   
}
