/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */

package org.sscraper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sscraper.utils.Log;
import org.sscraper.model.MovieInfo;
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
        
       // TODO guess the movie title
       for (int i = 0; i < mScrapers.size(); i++) {
           MovieInfo movie = mScrapers.get(i).findMovie(query, year);
           if (movie != null) {
               // TODO save movie to data base
               Response res = new Response(Status.OK);
               res.addMovie(movie);
               return res.toJsonString();               
           }
       }
       
       return (new Response(Status.NOT_FOUND)).toJsonString();
    }
    
   
}
