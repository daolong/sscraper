/**  
 *  Copyright (C) 2015 dl@zidoo.tv
 */
package org.sscraper.scraper;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.sscraper.Status;
import org.sscraper.model.Actor;
import org.sscraper.model.MovieInfo;
import org.sscraper.model.SearchResult;
import org.sscraper.model.tmdb.Casts;
import org.sscraper.model.tmdb.TmdbConfig;
import org.sscraper.model.tmdb.TmdbSearchResult;
import org.sscraper.network.HttpUtils;
import org.sscraper.utils.Log;

public class TmdbScraper extends ScraperBase {
    private final static String NAME = "TMDB";
    
    private final static String API_KEY = "57983e31fb435df4df77afb854740ea9";
    private final static String SEARCH_URL = "http://api.tmdb.org/3/search/movie?";
    private final static String CONFIG_URL = "http://api.tmdb.org/3/configuration?";
    private final static String INFO_URL = "http://api.tmdb.org/3/movie/";
  
    public TmdbScraper() {
        super(NAME);
    }

    public MovieInfo findMovie(String name, String year) {
        SearchResult search = searchMovie(name, year);
        if (search == null) {
            Log.d(NAME, "cat not find information of <" + name + ">");
            return null;
        }
        
        Log.d(NAME, "find movie : " + HttpUtils.decodeHttpParam(name, "UTF-8"));
        
        MovieInfo info = new MovieInfo(name);
        /*
        byte[] bytes;
        try {
            bytes = search.getTitle().getBytes();
            for (int i = 0 ; i < bytes.length; i++) {
                Log.d(NAME, String.format("title byte[%d] : 0x%x", i, bytes[i]));
            }
            
            String newTitel = new String(bytes, "iso8859-1");
            bytes = newTitel.getBytes("iso8859-1");
            for (int i = 0 ; i < bytes.length; i++) {
                Log.d(NAME, String.format("new title byte[%d] : 0x%x", i, bytes[i]));
            }
            
        } catch (Exception e) {}
        */
        
        info.setTitle(search.getTitle());
        info.setOtherTitle(search.getOriginalTitle());
        info.setReleaseDate(search.getReleaseDate());
        info.setDuration(0L);
        info.setLanguage(search.getOriginalLanguage());
        info.setOverView(search.getOverView());
        info.setVoteAverage(search.getVoteAverage());
        
        // get casts, director, script writer ...
        Casts cast = getCasts(search.getId());
        if (cast != null) {
            ArrayList<Casts.Cast> casts = cast.getCasts();
            if (casts != null && casts.size() > 0) {
                for (int i = 0; i < casts.size(); i++) {
                    Actor actor = new Actor(casts.get(i).getName(), casts.get(i).getCharacter());
                    info.addActor(actor);
                }
            }
            
            ArrayList<Casts.Crew> crews = cast.getCrews();
            if (crews != null && crews.size() > 0) {
                for (int i = 0; i < crews.size(); i++) {
                    if (crews.get(i).getJob().equals("Director")) {
                        info.addDirector(crews.get(i).getName());
                    }
                }
            }
        }
        
        // get poster image url
        TmdbConfig config = getConfig();        
        if (config != null) {
            String base_url = config.getImages().getBase_url();
            if (base_url != null) {
                String url;
                if (config.getImages().getPoster_sizes().size() > 0) {   
                    int last = config.getImages().getPoster_sizes().size() - 1;
                    url = base_url + config.getImages().getPoster_sizes().get(last) + "/" + search.getPosterPath();         
                } else {
                    url = base_url + "original/" + search.getPosterPath();
                }
                info.setPostorImageUrl(url);
            }            
        } 
        
        info.setSource(NAME);
        
        return info;
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
            int ret = search.parseJson(response);
            if (ret == Status.OK) 
                return search;
            else {
                Log.d(NAME, "searchMovie : parserJson fail");
                return null;
            }            
        } else {
            Log.d(NAME, "searchMovie : HttpGet " + url + " fail!");
            return null;
        }
    }
    
    /**
     * Get actors & directors ...
     * @param id The movie data base id 
     * @return
     */
    private Casts getCasts(Long id) {
        String url = INFO_URL + id + "/casts?api_key=" + API_KEY + "&language=zh"; 
        String response = HttpUtils.httpGet(url);
        if (response != null) {
            Casts casts = new Casts();
            if (casts.parseJson(response) == Status.OK) {
                return casts;
            } else {
                Log.d(NAME, "getCasts : parse json fail");
            }
        } else {
            Log.d(NAME, "getCasts : no response!");
        }
        
        return null;
    }
    
    private TmdbConfig getConfig() {
        String url = CONFIG_URL + "api_key=" + API_KEY;
        String response = HttpUtils.httpGet(url);
        if (response != null) {
            TmdbConfig config = new TmdbConfig();
            if (config.parseJson(response) == Status.OK) {
                return config;
            } else {
                Log.d(NAME, "getConfig : parse json fail");
            }
        } else {
            Log.d(NAME, "getConfig : no response!");
        }
        
        return null;
    }
}
