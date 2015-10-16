package org.sscraper.scraper;

import org.sscraper.model.MovieInfo;
import org.sscraper.model.SearchResult;
import org.sscraper.network.HttpUtils;

public class TmdbScraper extends ScraperBase {
    private final static String NAME = "TMDB";
    
    private final static String API_KEY = "57983e31fb435df4df77afb854740ea9";
    private final static String SEARCH_URL = "http://api.tmdb.org/3/search/movie?";
    
    
  
    public TmdbScraper() {
        super(NAME);
    }

    public MovieInfo findMovie(String name, String year) {
       
    }
    
    private SearchResult searchMovie(String name, String year) {
        String url;
        if (year != null)
            url = SEARCH_URL + "api_key=" + API_KEY + "&query=" + name + "&year=" + year + "&language=zh";
        else 
            url = SEARCH_URL + "api_key=" + API_KEY + "&query=" + name + "&language=zh";
        
        String response = HttpUtils.httpGet(url);
        if (response != null) {
            
        } else {
            return null;
        }
        
        return null;
    }
}
