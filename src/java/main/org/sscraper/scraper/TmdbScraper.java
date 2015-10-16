/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.scraper;

import org.sscraper.Status;
import org.sscraper.model.MovieInfo;
import org.sscraper.model.SearchResult;
import org.sscraper.model.tmdb.Casts;
import org.sscraper.model.tmdb.TmdbConfig;
import org.sscraper.model.tmdb.TmdbSearchResult;
import org.sscraper.network.HttpUtils;

public class TmdbScraper extends ScraperBase {
    private final static String NAME = "TMDB";
    
    private final static String API_KEY = "57983e31fb435df4df77afb854740ea9";
    private final static String SEARCH_URL = "http://api.tmdb.org/3/search/movie?";
    private final static String CONFIG_URL = " http://api.tmdb.org/3/configuration?";
    private final static String INFO_URL = "http://api.tmdb.org/3/movie/";
  
    public TmdbScraper() {
        super(NAME);
    }

    public MovieInfo findMovie(String name, String year) {
        SearchResult search = searchMovie(name, year);
        if (search == null)
            return null;
        
        
        return null;
    }
    
    private SearchResult searchMovie(String name, String year) {
        String url;
        if (year != null)
            url = SEARCH_URL + "api_key=" + API_KEY + "&query=" + name + "&year=" + year + "&language=zh";
        else 
            url = SEARCH_URL + "api_key=" + API_KEY + "&query=" + name + "&language=zh";
        
        String response = HttpUtils.httpGet(url);
        if (response != null) {
            SearchResult search = new TmdbSearchResult();
            int ret = search.parseJason(response);
            if (ret == Status.OK) 
                return search;
            else
                return null;
        } else {
            return null;
        }
    }
    
    /**
     * Get actors & directors ...
     * @param id The movie data base id 
     * @return
     */
    private Casts getCasts(Long id) {
        
        
        return null;
    }
    
    private TmdbConfig getConfig() {
        String url = CONFIG_URL + "api_key=" + API_KEY;
        String response = HttpUtils.httpGet(url);
        if (response != null) {
            TmdbConfig config = new TmdbConfig();
            if (config.parseJson(response) == Status.OK) {
                return config;
            }
        }
        
        return null;
    }
}
